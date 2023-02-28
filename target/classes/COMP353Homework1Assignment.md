# <div align = "center"> COMP353 Homework 1 </div>
## <div align = "center"> Sam Bernau </div>

#### 1. [16 points] List the songs written by artists born no later than 1975, order the list by songTitle in ascending order. (songTitle, firstName, lastName, yearBorn)
##### Query 
``` sql
SELECT songTitle, firstName, lastName, yearBorn 
FROM song S, writtenBy W , artist A 
WHERE S.songCode = W.songCode --make sure song_code's match between tables
AND W.artistID = A.artistID --make sure artist_id's match between tables
AND A.yearBorn <= 1975 
ORDER BY songTitle ASC;
```
##### Query Output
| songTitle               | firstName | lastName  | yearBorn |
|:------------------------|:----------|:----------|:---------|
| Another Day in Paradise | Henry     | Brown     | 1966     |
| Breathless              | Francis   | McDermott | 1960     |
| Broken                  | Lisa      | Raymond   | 1973     |
| Days Go By              | Michael   | Agnelo    | 1975     |
| Fire                    | Lisa      | Raymond   | 1973     |
| Forgotten               | Heidi     | Helmut    | 1945     |
| Forgotten               | Bjorn     | Friedman  | 1945     |
| Goldilocks              | Lisa      | Raymond   | 1973     |
| Goodnight               | Bjorn     | Friedman  | 1945     |
| Hot As Hell             | John      | Stark     | 1971     |
| How Could You           | Heidi     | Helmut    | 1945     |
| I Have A Dream          | John      | Stark     | 1971     |
| Life Or Death           | Janet     | Brown     | 1972     |
| Mamma Mia               | Lisa      | Raymond   | 1973     |
| My Lullaby              | John      | Hopkins   | 1960     |
| My Oh My                | Steve     | Nash      | 1965     |
| One Of Us               | Lisa      | Raymond   | 1973     |
| Ooh La La               | Steve     | Nash      | 1965     |
| Smile                   | Jim       | Kate      | 1970     |
| Sweet Dreams            | Bjorn     | Friedman  | 1945     |

#### 2. [16 points] For every artist, find the number of songs rated top 10. List the count of songsin descending order.(firstName, lastName, countOfTopSong)
##### Query
``` sql
SELECT artist.firstName,artist.lastName, COUNT(*) AS top10Count
FROM song
         INNER JOIN writtenBy ON song.songCode = written_by.songCode
         INNER JOIN artist ON writtenBy.artistID = artist.artistID
         INNER JOIN topSongs ON song.songCode = topSongs.songCode
WHERE topSongs.rating >= 10
GROUP BY artist.firstName, artist.lastName
ORDER BY top10Count DESC;
```
##### Query Output
| firstName | lastName | top10Count |
|:----------|:---------|:-----------|
| John      | Stark    | 1          |
| Henry     | Brown    | 1          |

#### 3. [17 points] List the maximum, minimum, and average of cdSold per artist. Order by the artist’s first name in ascending order (firstName, lastName, maxNumber, minNumber, avgNumber)
##### Query
``` sql
SELECT A.firstName, A.lastName, MAX(cd.numberSold) as maxSold, MIN(cd.numberSold) as minSold, ROUND(AVG(cd.numberSold), 2) as avgSold
FROM artist A
JOIN member M ON A.artistID = M.artistID
JOIN musicalGroup MG on M.groupCode = MG.groupCode
JOIN cd ON cd.groupCode = MG.groupCode
GROUP BY A.firstName, A.lastName
order by A.firstName;
```
##### Query Output
| firstName | lastName  | maxSold | minSold | avgSold   |
|:----------|:----------|:--------|:--------|:----------|
| Bjorn     | Friedman  | 800000  | 40000   | 378000    |
| Francis   | McDermott | 800000  | 750000  | 775000    |
| Heidi     | Helmut    | 500000  | 40000   | 270000    |
| Henry     | Brown     | 800000  | 25000   | 402666.67 |
| Jacky     | Chen      | 800000  | 100000  | 450000    |
| Janet     | Brown     | 800000  | 40000   | 421111.11 |
| John      | Stark     | 500000  | 40000   | 270000    |
| Keith     | Urban     | 900000  | 89000   | 465600    |
| Lisa      | Raymond   | 500000  | 40000   | 270000    |
| Michael   | Agnelo    | 900000  | 89000   | 459750    |
| Michelle  | Agnelo    | 800000  | 100000  | 450000    |
| Steve     | Nash      | 800000  | 750000  | 775000    |

#### 4. [17 points] How many top songs has each recording label produced? Order by the number of songs in descending order. 
- Note: In this database, a song can be part of multiple CDs 
- Hint: Use Distinct to get the most accurate result. (labelName, numberOfSong)
##### Query
```sql

```
##### Query Output

#### 5. [17 points] Identify the artists that don’t have a top 5 song and were born no later than 1960 (firstName, lastName, yearBorn)
##### Query
```sql

```
##### Query Output

#### 6. [17 points] Identify the artists that have at least 2 top songs and at least 1 top cd. (artistID, firstName, lastName, countOfTopSongs, countOfTopCDs)
##### Query
```sql

```
##### Query Output

