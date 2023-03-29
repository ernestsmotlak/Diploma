import spacy
import sys

# Load the spaCy model for Croatian
nlp = spacy.load("hr_core_news_md")

# Read the input string from the Java program
input_string = sys.stdin.readline().strip()

# Tokenize and lemmatize the input text
doc = nlp(input_string)
lemmas = [token.lemma_ for token in doc]

# Output the lemmatized text
output_string = " ".join(lemmas)
print(output_string)
