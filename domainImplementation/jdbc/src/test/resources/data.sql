USE MUSIC_REP;

INSERT INTO ARTIST(GROUP_NAME, MONTHLY_LIST) VALUES
("LINKIN PARK", 150000), 
("MUSE",98000),
("LADY GAGA", 145000);

INSERT INTO ARTIST_DATA VALUES
(1, "USA", "ENG", 1996),
(2, "ENG", "ENG", 1994),
(3, "USA", "ENG", 2005);

INSERT INTO USER (USERNAME) VALUES
("asomasekas"),
("Bombis"),
("user6789");

INSERT INTO USER_DATA VALUES
(1, "Alvaro", 666666666, "ESP", 22),
(2, "Berry", 436656998, "NL", 33),
(3, "Niko", 123321555, "IT", 19);

INSERT INTO ALBUM (ID_ARTIST, ALBUM_NAME, QUANTITY_SONGS) VALUES
(1, "Meteora", 13),
(2, "Origin of Symmetry", 11),
(3, "Born this way", 22);

INSERT INTO SONG (ID_ALBUM, ID_ARTIST, SONG_NAME, DURATION) VALUES
(1,1,"Faint", 162),
(1,1,"Somewhere I Belong", 214),
(2,2,"New Born", 360),
(2,2,"Plug in Baby", 220),
(3,3,"Americano", 247),
(3,3,"Government Hooker", 254);

INSERT INTO PLAYLIST (QUANTITY_SONGS, PLAYLIST_NAME, DESCRIPTION, DURATION) VALUES
(2, "Dancing~", "Some songs to dance to", 501),
(3, "Mimir", "Songs to relax to (kinda)", 794);

INSERT INTO PLAYLIST_SONG VALUES
(1, 5),
(1, 6),
(2, 2),
(2, 3),
(2, 4);

INSERT INTO PLAYLIST_USER VALUES
(1,1),
(1,3),
(2,2);
