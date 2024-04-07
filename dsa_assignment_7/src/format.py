import json
import re

users_messages: dict[str, str] = {}

with open("./../texts/Direct Messages - Angelicy [1148142362233671691].json", "r", encoding="utf8") as file:
    contents = json.loads(file.read())

    messages = contents["messages"]

    for message in messages:
        author = message["author"]["name"]
        content = message["content"]
        existing_messages = users_messages.get(author, None)

        if existing_messages is None:
            existing_messages = ""
            users_messages[author] = existing_messages

        users_messages[author] = users_messages[author] + content + ". \n"

for user, messages in users_messages.items():
    with open(f"./../texts/{user} - [{','.join(list(users_messages.keys()))}]", "w+", encoding="utf8") as file:
        file.write(messages)
