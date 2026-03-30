# Exercice sur la quatification de produit (product Quantization)

*Contexte*

On dispose d'une base de données de vecteurs de dimension D=128. On utilise une quantification de produit (PQ) avec les paramètres suivants :
- m = 8 (nombre de sous-espaces).
- k = 256 (nombre de centroïdes par sous-espace, codés sur 1 octet).
- Les vecteurs originaux sont stockés en float32 (4 octets par dimension).

## A. Analyse de la compression

- 1.a : taille en octets d'un vecteur = $128 * 4 = 512$
- 1.b : taille en octets d'un vecteur après quantification = $8 sous-espaces * 1 octets = 8$
- 1.c : taux de compressions = $512 / 8 = 64$
- 1.d : dimension des sous vecteurs = $128 / 8 = 16$

2.a : $10 000 000vecteurs * 512octets = 5 120 000 000 octets = 5,12 Go$
2.b : $10 000 000 * 8 = 80 000 000 octets = 80 Mo$

## B. Construction de la lookup table

- 1.a : distance euclidienne carré d(q2, c2, 42) = $0,2$
- 1.b : distance euclidienne carré d(q2, c2, 105) = $1,06$

## C. Recherche ADC



