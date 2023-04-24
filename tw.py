import tweepy

# Enter your API keys
consumer_key = 'your_consumer_key'
consumer_secret = 'your_consumer_secret'
access_token = 'your_access_token'
access_token_secret = 'your_access_token_secret'

# Authenticate with Twitter API
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

# Set search parameters
query = 'happy'
geocode = '40.7128,-74.0060,10mi'

# Search for tweets
tweets = api.search(q=query, geocode=geocode)

# Print the text of the tweets
for tweet in tweets:
    print(tweet.text)
import twint

# Configure search parameters
c = twint.Config()
c.Hide_output=True
c.Pandas_clean=True
c.Pandas=True
c.Search="#nfl"
c.Since='2019-12-01'
c.Until='2019-12-02'

twint.run.Search(c)
# Remove this line to remove search limit
c.Limit = 5

# Run search and print tweets
twint.run.Search(c)
tweets = twint.storage.panda.Tweets_df
print(tweets['tweet'])
