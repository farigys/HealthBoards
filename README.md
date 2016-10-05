# HealthBoards
Codes for downloading and analyzing healthboards.com data

HealthBoards Analysis:

Sequence of running the codes:

Data Downolad and Processing:

1. HealthBoardDownloader/HealthBoardDownLoader.java
Downloads the forums specified within the code. The code requires two python codes: ListAllPages.py and downloadWebPages.py present in the workspace.

2. HTMLConversion/HTMLconverter.java
Creates JSON files from the downloaded HTML files. Names of the forums are provided within the code.

3. HealthBoardsExperiments/cacheFileCreate.java:
Creates cache files "userInfo.txt" and "userPostInfo.txt" (see below)

Analysis:

1. HealthBoardsExperiments/PMIAnalysis.java:
Creates global list of word unigrams and bigrams with their PMIs associated with being in last posts

2. HealthBoardsExperiments/PMIAnalysisOnConditions.java:
Creates condition-depepndant lists of word unigrams and bigrams with their PMIs

3. HealthBoardsExperiments/PsyLingPmiAnalysis.java:  
Creates global list of LIWC word categories with their last post PMIs

4. HealthBoardsExperiments/PsyLingPmiAnalysisOnConditions.java
Same as previous, but condition-dependant

5. HealthBoardsExperiments/findTopPosters.java
Finds top five/ten (provided inside the code) posters in a forum who have not been active for at least a year but was active for more than a year after their first activity

6. HealthBoardsExperiments/psyLingCacheCreate.java
Creates a list for each post that contains all word unigrams classified into 68 LIWC word categories


Data Files Description (Files are global and/or condition-based):
1. LastPostWordPMI.csv/LastPostWordPMI1.csv: List of words that occured in last posts with their PMIs associated with last posts

2. LastPostWordPMIBigram.csv: List of word bigrams that occured in last posts with their PMIs associated with last posts

3. postDistribution.txt: Number of posts posted in individual hours of a day

4. PsyLingPMI.csv: List of psycholinguistic classes from LIWC that occured in last posts with their PMIs associated with last posts

5. UserInfo.txt: List of users with their demographics and post related info including number of posts and blogs, first and last posts etc. 

6. FileLists.txt: List of threads that has been downloaded. The list only contains the root link of a thread that spans multiple pages.

7. PagesCrawled.txt: List of threads that has been downloaded. The list only contains all the links of a thread that spans multiple pages.

8. userPostInfo.txt: Lists of posts with the poster, condition, threadId, postId, time and type 

9. topPosters: List of top five/ten posters in a forum who have not been active for at least a year but was active for more than a year after their first activity

10. PsyLingCount.csv: Collection of posts that contains all word unigrams classified into 68 LIWC word categories for each post
