import twint
import tweepy
import os

# enter your Twitter API keys here
from dotenv import load_dotenv

# load the environment variables from .env file
load_dotenv()

# get the Twitter API keys and access tokens from environment variables
consumer_key = os.getenv('CONSUMER_KEY')
consumer_secret = os.getenv('CONSUMER_SECRET')
access_token = os.getenv('ACCESS_TOKEN')
access_token_secret = os.getenv('ACCESS_TOKEN_SECRET')


# Authenticate with Twitter API
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

# Set search parameters
query = 'slovenija'
geocode = '46.0569,14.5058,300mi'

# Search for tweets
tweets = api.search_tweets(q=query, geocode=geocode)

# Print the text of the tweets
for tweet in tweets:
    print(tweet.text)

