#!/usr/bin/env bash

NOTE_FILE_NAME="release_notes.txt"
rm "$NOTE_FILE_NAME"

#Define apps name
cm="Caloriemama"
sp="Kensapo"
kv="Knavi"


echo "================================================"
echo "Tag name: ${CIRCLE_TAG}"
echo "================================================"

TAG_PREFIX=$(echo $CIRCLE_TAG | cut -d "-" -f1)

#Get tag url
REQUEST_URL="https://api.github.com/repos/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/releases/tags/${CIRCLE_TAG}"

echo "================================================"
echo "Request Url: ${REQUEST_URL}"
echo "================================================"

#Print tag name to file
echo $CIRCLE_TAG > "$NOTE_FILE_NAME"

#Request api
curl -H "Authorization: token $GITHUB_ACCESS_TOKEN" "${REQUEST_URL}" \
| python -c 'import json,sys;obj=json.load(sys.stdin);print obj["body"];' >> "$NOTE_FILE_NAME"

#Upload fabric beta
./gradlew :app:assemble${!TAG_PREFIX}Debug crashlyticsUploadDistribution${!TAG_PREFIX}Debug