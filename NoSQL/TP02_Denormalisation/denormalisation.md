- **primary key**
- *foreign key*

</br>

# Naive

- Person:
    - **idPerson**
    - firstName
    - lastName
    - birthDate

- Film:
    - **idFilm**
    - title
    - year
    - summary
    - *idGenre*

- Actor:
    - **idPerson**
    - **idFilm**
    - role

- Director:
    - **idPerson**
    - **idFilm**

- Genre:
    - **idGenre**
    - label


```json
{
  "Person": [
    {
      "idPerson": 1,
      "firstName": "David",
      "lastName": "Cronenberg",
      "birthDate": "1943"
    },
    {
      "idPerson": 2,
      "firstName": "Ed",
      "lastName": "Harris",
      "birthDate": "1950"
    },
    {
      "idPerson": 3,
      "firstName": "Vigo",
      "lastName": "Mortensen",
      "birthDate": "1958"
    },
    {
      "idPerson": 4,
      "firstName": "Maria",
      "lastName": "Bello",
      "birthDate": "1967"
    }
  ],
  "Genre": [
    {
      "idGenre": 1,
      "label": "crime"
    }
  ],
  "Film": [
    {
      "idFilm": 1,
      "title": "A History of Violence",
      "year": "2005",
      "summary": "Tom Stall, a humble family man and owner of a popular neighborhood restaurant, lives a quiet but fulfilling existence in the Midwest. One night Tom foils a crime at his place of business and, to his chagrin, is plastered all over the news for his heroics. Following this, mysterious people follow the Stalls' every move, concerning Tom more than anyone else. As this situation is confronted, more lurks out over where all these occurrences have stemmed from compromising his marriage, family relationship and the main characters' former relations in the process.",
      "idGenre": 1
    }
  ],
  "Director": [
    {
      "idPerson": 1,
      "idFilm": 1
    }
  ],
  "Actor": [
    {
      "idPerson": 2,
      "idFilm": 1,
      "role": "Carl Fogarty"
    },
    {
      "idPerson": 3,
      "idFilm": 1,
      "role": "Tom Stall"
    },
    {
      "idPerson": 4,
      "idFilm": 1,
      "role": "Richie Cusack"
    }
  ]
}
```


</br>

# Optimised for research based on films

- Film:
    - **idFilm**
    - title
    - year
    - summary
    - director:
        - firstName
        - lastName
        - birthDate
    - list(actors):
        - firstName
        - lastName
        - birthDate
        - role

```json
{
    "title": "A History of Violence",
    "year": "2005",
    "summary": 
        "
        Tom Stall, a humble
        family man and owner of a popular neighborhood restaurant, lives a quiet but fulfilling
        existence in the Midwest. One night Tom foils a crime at his place of business and, to
        his chagrin, is plastered all over the news for his heroics. Following this, mysterious
        people follow the Stalls' every move, concerning Tom more than anyone else. As this
        situation is confronted, more lurks out over where all these occurrences have
        stemmed from compromising his marriage, family relationship and the main
        characters' former relations in the process.
        ",
    "genre": "crime",
    "director": {
        "firstName": "David",
        "lastName": "Cronenberg",
        "birthDate": "1943"
    },
    "actors": {
        {
            "firstName": "Ed",
            "lastName": "Harris",
            "birthDate": "1950",
            "role": "Carl Fogarty"
        },
        {
            "firstName": "Vigo",
            "lastName": "Mortensen",
            "birthDate": "1958",
            "role": "Tom Stall"
        },
        {
            "firstName": "Maria",
            "lastName": "Bello",
            "birthDate": "1967",
            "role": "Richie Cusack"
        }
    }  
}
```