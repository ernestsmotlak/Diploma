import spacy

nlp = spacy.load("hr_core_news_md")

#text = "Ovo je primjer rečenice za lematizaciju."
text = "Kako je lepo kada su ulice pune ljudi smeha gužve i života nadam se da ćemo se uskoro vratiti tom normalnom stanju optimizam život beograd"
# text1 = "Kako je lepo kada su ulice pune ljudi, smeha, gužve i života. Nadam se da ćemo se uskoro vratiti tom normalnom stanju. #optimizam #života #beograd  "
# text4 = "Zašto se nešto ne može platiti karticom ako se može kupiti na rate? Ovo je čista diskriminacija ljudi koji nemaju novca da plate sve odjednom. #nepravda #bankelopovi"
# text2 = "nekoliko rečenica na srpskom jeziku"
# text3 = "rečeno ili riječenooooo"
doc = nlp(text)

lemmas = []
for token in doc:
    lemmas.append(token.lemma_)

print(lemmas)
