import tweepy
import random

# Enter your API keys
consumer_key = 'YNB73movKB4h3Rb04AVz1tUAL'
consumer_secret = 'fAEwHSDCY7fzNB6aDVvox3sdoyExt2wQxTJTQOeRRA8sVujvQ8'
access_token = '1091307256866635776-PkUNrqygEheSy6VmY4idbupZrZwkE2'
access_token_secret = 'DHzTL4I3tqlJKFjzyDZsdRjwEVF0lnGFSaqlkUVzZvAQ5'

# Authenticate with Twitter API
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

# Set search parameters
query = ' '
language = 'en'

# Search for tweets
tweets = tweepy.Cursor(api.search_tweets,
                       q=query,
                       lang=language,
                       tweet_mode='extended').items(100)

# Get 5 random tweets from the search results
# random_tweets = random.sample(tweets, k=5)

# Print the text of the random tweets
for tweet in tweets:
    print(tweet.text)
