# <div align = "center"> COMP353 Homework 1 </div>
## <div align = "center"> Sam Bernau & Shane Lievens </div>

## DDL/DML Script 
```sql
CREATE TABLE recording_label(
	label_id varchar(50) PRIMARY KEY,
	label_name varchar(50) NOT NULL,
	location varchar(50) NOT NULL
);

CREATE TABLE artist(
	artist_id varchar(50) PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	year_born numeric NOT NULL
);

CREATE TABLE musical_Group(
	group_code varchar(50) PRIMARY KEY,
	group_name varchar(50) NOT NULL
);

CREATE TABLE cd(
	cd_code varchar(50) PRIMARY KEY,
	cd_title varchar(50) NOT NULL,
	number_sold numeric NOT NULL,
	year numeric NOT NULL,
	label_id varchar(50) NOT NULL,
	FOREIGN KEY(label_id) REFERENCES recording_label(label_id),
	group_code varchar(50) NOT NULL,
	FOREIGN KEY(group_code) REFERENCES musical_group(group_code)
);

CREATE TABLE song(
	song_code varchar(50) PRIMARY KEY,
	song_title varchar(50) NOT NULL
);

CREATE TABLE top_CDs(
	cd_code varchar(50) NOT NULL,
	FOREIGN KEY (cd_code) REFERENCES cd(cd_code),
	year numeric NOT NULL,
	PRIMARY KEY(cd_code, year),
	rating numeric NOT NULL
);

CREATE TABLE composed_of(
	cd_code varchar(50) NOT NULL,
	FOREIGN KEY(cd_code) REFERENCES cd(cd_code),
	song_code varchar(50) NOT NULL,
	FOREIGN KEY(song_code) REFERENCES song(song_code),
	PRIMARY KEY (cd_code, song_code),
	track_number numeric NOT NULL
);

CREATE TABLE top_songs(
	song_code varchar(50) NOT NULL,
	FOREIGN KEY(song_code) REFERENCES song(song_code),
	year numeric NOT NULL,
	PRIMARY KEY(song_code, year),
	rating numeric NOT NULL
);

CREATE TABLE member(
	group_code varchar(50) NOT NULL,
	FOREIGN KEY(group_code) REFERENCES musical_group(group_code),
	artist_id varchar(50) NOT NULL,
	FOREIGN KEY(artist_id) REFERENCES artist(artist_id),
	from_date numeric NOT NULL,
	PRIMARY KEY(group_code, artist_id, from_date),
	to_date numeric NOT NULL
);

CREATE TABLE written_by(
	song_code varchar(50) NOT NULL,
	FOREIGN KEY (song_code) REFERENCES song(song_code),
	artist_id varchar(50) NOT NULL,
	FOREIGN KEY(artist_id) REFERENCES artist(artist_id),
	PRIMARY KEY(song_code, artist_id)
);
```

### Homework Answers

#### 1. [16 points] List the songs written by artists born no later than 1975, order the list by songTitle in ascending order. (songTitle, firstName, lastName, yearBorn)
##### Query 
```sql
SELECT song."songTitle", "artist"."firstName", "artist"."lastName", "artist"."yearBorn"
FROM song
JOIN "writtenBy" ON song."songCode" = "writtenBy"."songCode"
JOIN "artist" ON "writtenBy"."artistID" = "artist"."artistID"
WHERE "artist"."yearBorn" <= 1975
ORDER BY song."songTitle" ASC;
```
##### Query Output
| song\_title             | first\_name | last\_name | year\_born |
|:------------------------|:------------|:-----------|:-----------|
| Another Day in Paradise | Henry       | Brown      | 1966       |
| Breathless              | Francis     | McDermott  | 1960       |
| Broken                  | Lisa        | Raymond    | 1973       |
| Days Go By              | Michael     | Agnelo     | 1975       |
| Fire                    | Lisa        | Raymond    | 1973       |
| Forgotten               | Heidi       | Helmut     | 1945       |
| Forgotten               | Bjorn       | Friedman   | 1945       |
| Goldilocks              | Lisa        | Raymond    | 1973       |
| Goodnight               | Bjorn       | Friedman   | 1945       |
| Hot As Hell             | John        | Stark      | 1971       |
| How Could You           | Heidi       | Helmut     | 1945       |
| I Have A Dream          | John        | Stark      | 1971       |
| Life Or Death           | Janet       | Brown      | 1972       |
| Mamma Mia               | Lisa        | Raymond    | 1973       |
| My Lullaby              | John        | Hopkins    | 1960       |
| My Oh My                | Steve       | Nash       | 1965       |
| One Of Us               | Lisa        | Raymond    | 1973       |
| Ooh La La               | Steve       | Nash       | 1965       |
| Smile                   | Jim         | Kate       | 1970       |
| Sweet Dreams            | Bjorn       | Friedman   | 1945       |

#### 2. [16 points] For every artist, find the number of songs rated top 10. List the count of songsin descending order.(firstName, lastName, countOfTopSong)
##### Query
```sql
SELECT artist."firstName", artist."lastName", COUNT(DISTINCT "topSongs"."songCode") as countOfTopSong
FROM artist
INNER JOIN "writtenBy" ON artist."artistID" = "writtenBy"."artistID"
INNER JOIN "song" ON "writtenBy"."songCode" = "song"."songCode"
INNER JOIN "topSongs" ON "song"."songCode" = "topSongs"."songCode"
WHERE "topSongs"."rating" >= 10
GROUP BY artist."artistID"
ORDER BY countOfTopSong DESC;
```
##### Query Output
| first\_name | last\_name | top_10_count |
|:------------|:-----------|:-------------|
| John        | Stark      | 1            |
| Henry       | Brown      | 1            |

#### 3. [17 points] List the maximum, minimum, and average of cdSold per artist. Order by the artist’s first name in ascending order (firstName, lastName, maxNumber, minNumber, avgNumber)
##### Query
```sql
SELECT "artist"."firstName", "artist"."lastName", MAX(cd."numberSold") AS maxNumber, 
	MIN(cd."numberSold") AS minNumber, AVG(cd."numberSold") AS avgNumber
FROM cd
INNER JOIN "musicalGroup" ON "cd"."groupCode" = "musicalGroup"."groupCode"
INNER JOIN "member" ON "musicalGroup"."groupCode" = "member"."groupCode"
INNER JOIN "artist" ON "member"."artistID" = "artist"."artistID"
GROUP BY "artist"."firstName", "artist"."lastName"
ORDER BY "artist"."firstName" ASC;
```
##### Query Output
| first\_name | last\_name | max\_sold | min\_sold | avg\_sold |
|:------------|:-----------|:----------|:----------|:----------|
| Bjorn       | Friedman   | 800000    | 40000     | 378000    |
| Francis     | McDermott  | 800000    | 750000    | 775000    |
| Heidi       | Helmut     | 500000    | 40000     | 270000    |
| Henry       | Brown      | 800000    | 25000     | 402666.67 |
| Jacky       | Chen       | 800000    | 100000    | 450000    |
| Janet       | Brown      | 800000    | 40000     | 421111.11 |
| John        | Stark      | 500000    | 40000     | 270000    |
| Keith       | Urban      | 900000    | 89000     | 465600    |
| Lisa        | Raymond    | 500000    | 40000     | 270000    |
| Michael     | Agnelo     | 900000    | 89000     | 459750    |
| Michelle    | Agnelo     | 800000    | 100000    | 450000    |
| Steve       | Nash       | 800000    | 750000    | 775000    |

#### 4. [17 points] How many top songs has each recording label produced? Order by the number of songs in descending order. 
- Note: In this database, a song can be part of multiple CDs 
- Hint: Use Distinct to get the most accurate result. (labelName, numberOfSong)
##### Query
```sql
SELECT DISTINCT label_name, COUNT(DISTINCT top_songs.song_code) as number_of_song
FROM recording_label
         JOIN cd ON recording_label.label_id = cd.label_id
         JOIN composed_of ON composed_of.cd_code = cd.cd_code
         JOIN top_songs ON top_songs.song_code = composed_of.song_code
GROUP BY recording_label.label_id
ORDER BY number_of_song DESC;
```
##### Query Output
| label\_name            | number\_of\_song |
|:-----------------------|:-----------------|
| A&M Records            | 11               |
| Disney Records         | 10               |
| Reprise Records        | 9                |
| Universal Records      | 6                |
| Disney Records         | 4                |
| Gray Dot Records       | 4                |
| Country Club           | 3                |
| Parkwood Entertainment | 1                |
#### 5. [17 points] Identify the artists that don’t have a top 5 song and were born no later than 1960 (firstName, lastName, yearBorn)
##### Query
```sql
SELECT DISTINCT first_name, last_name, year_born
FROM top_songs T, written_by S, artist
WHERE artist.artist_id = S.artist_id AND year_born <= 1960
GROUP BY artist.first_name, last_name, year_born, T.rating
HAVING T.rating > 5;
```
##### Query Output
| first\_name | last\_name | year\_born |
|:------------|:-----------|:-----------|
| John        | Hopkins    | 1960       |
| Francis     | McDermott  | 1960       |
| Bjorn       | Friedman   | 1945       |
| Heidi       | Helmut     | 1945       |
#### 6. [17 points] Identify the artists that have at least 2 top songs and at least 1 top cd. (artistID, firstName, lastName, countOfTopSongs, countOfTopCDs)
##### Query
```sql
SELECT
    artist.artist_id,
    artist.first_name,
    artist.last_name,
    COUNT(DISTINCT top_songs.song_code) AS count_of_top_songs,
    COUNT(DISTINCT top_cds.cd_code) AS count_of_top_cds
FROM
    artist
        JOIN written_by ON artist.artist_id = written_by.artist_id
        JOIN top_songs ON written_by.song_code = top_songs.song_code
        JOIN composed_of  ON written_by.song_code = composed_of.song_code
        JOIN cd  ON composed_of.cd_code = cd.cd_code
        JOIN top_cds  ON cd.cd_code = top_cds.cd_code AND top_cds.year = cd.year
GROUP BY
    artist.artist_id,
    artist.first_name,
    artist.last_name
HAVING
    COUNT(DISTINCT top_songs.song_code) >= 2 AND COUNT(DISTINCT top_cds.cd_code) >= 1;
```
##### Query Output
| artist\_id | first\_name | last\_name | count\_of\_top\_songs | count\_of\_top\_cds |
|:-----------|:------------|:-----------|:----------------------|:--------------------|
| A1         | Bjorn       | Friedman   | 3                     | 7                   |
| A2         | Heidi       | Helmut     | 2                     | 3                   |
| A3         | John        | Stark      | 2                     | 6                   |
| A8         | Lisa        | Raymond    | 4                     | 5                   |


