import requests
from bs4 import BeautifulSoup
import random

def get_random_tweets():
    url = 'https://twitter.com/home'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36'
    }

    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.content, 'html.parser')
    tweet_elements = soup.find_all('span')

    tweets = []
    for tweet in tweet_elements:
        # try:
        #     content = tweet.find('span').text.strip()
        #     tweets.append(content)
        # except AttributeError:
        #     pass
        tweets.append(tweet.text)
    return tweets

random_tweets = get_random_tweets()

if len(random_tweets) >= 1:
    for i in range(len(random_tweets)):
        print(f'Tweet {i+1}:')
        print(random_tweets[i])
        print('------')
else:
    print('Not enough tweets found.')
