# WebCrawlerApp

Java web crawler 

Simple java Web crawler to crawl web pages based on the entered data and collect statistics (inclusions certain word(s) on pages) 
GUI allows you to set word(s) for searching, searching page url, max depth (allows you to only crawls pages reached within the number of clicks from the starting page) and max pages for counting.
By clicking SUBMIT you start searching process, on appearing window click GET RESULT - you will see top ten pages with max amount of searching words inclusions.
Outputs data to a csv file.
Outputs the top ten pages sorted by total inclusions to a separate csv file. 

Run application or jar file.

Output:

https://www.wikipedia.org/page1.html 8 4 5 0 17

https://www.wikipedia.org/page2.html 11 5 9 0 25

https://www.wikipedia.org/page3.html 1 4 3 0 8

where:

https://www.wikipedia.org/page2.html - start url;
	word1 - 11 hits;
	word2 - 5 hits;
	word3 - 9 hits;
	word4 - 0 hits;
	total inclusions - 25 hits

Requirements:

Maven 3.6.0

JDK 11.0.1

JavaFX 11.0.2

For program execution:
cd to project root folder in command line

mvn compile

mvn exec:java -Dexec.mainClass=test.chuvasova.webcrawler.CrawlerApp
