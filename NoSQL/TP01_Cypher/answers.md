# Exercice 3 :

- Question 1 : 

```sql
match (osm:OSMNode) return count(osm) 
```
</br>

- Question 2 : 

```sql
match (osm:OSMNode) return * limit 50
```

</br>

- Question 3 : 

```sql
match (osm:OSMNode) 
with osm.name as n  
where n is not null 
return count (distinct n)
```

</br>

- Question 4 : 

```sql
match (osm:OSMNode) 
with osm.name as n  
where n is not null
order by n
return distinct n limit 50
```

</br>

- Question 5 : 

```sql
match (poi:PointOfInterest)
where poi.name = 'Delacorte Musical Clock'
return poi.type
```

</br>

- Question 6 : 

```sql
match (poi:PointOfInterest)
where poi.type = 'clock'
return count(poi)
```

</br>

- Question 7 : 

```sql
match (poi1:PointOfInterest)
match (poi2:PointOfInterest{name: 'Delacorte Musical Clock'})
where point.distance(poi1.location, poi2.location) <= 200
and poi1.name <> poi2.name
return count(*)
```

</br>

# Exercice 4 :

- Question 1 : 

```sql
-- Sans label
explain
match (poi)
where poi.name = 'Delacorte Musical Clock'
return poi
-- On récupère toutes les 69 165 lignes
-- puis on filtre

-- Avec Label
explain
match (poi:PointOfInterest)
where poi.name = 'Delacorte Musical Clock'
return poi
-- On récupère seulement la table POI
-- puis on filtre
-- Better performances
```

</br>

- Question 2 : 

```sql
-- Display existing indexes
show indexes

-- Adding an index
create index index_poi_name for (poi:PointOfInterest) on (poi.name)
```

</br>

- Question 1 : 

```sql
-- Sans label après ajout index
explain
match (poi)
where poi.name = 'Delacorte Musical Clock'
return poi
-- On récupère toujours toutes les lignes
-- puis on filtre

-- Avec Label après ajout index
explain
match (poi:PointOfInterest)
where poi.name = 'Delacorte Musical Clock'
return poi
-- On utilise l'index et l'on récupère directement 2 lignes
```