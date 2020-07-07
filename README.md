# WebCrawlerApp

Java web crawler 

Simple java Web crawler to crawl web pages on predefined url and collect statistics (inclusions certain word(s) on pages) 
Basically you can set word(s) for searching, max depth (allows you to only crawls pages reached within the number of clicks from the starting page) and max pages for counting.

Crawl start searching from start url, defining the depth of the crawl and number of pages to crawl and collect data.

Outputs data to a csv file.
Outputs the top ten pages sorted by total inclusions to a separate csv file. 

How to crawl

Set input:
 
start url = https://www.wikipedia.org/
max link depth = 2
max page count = 3
input keywords = Tesla, Musk, Gigafactory, Elon Mask

Run application or jar file.

Output:

https://www.wikipedia.org/page1.html 8 4 5 0 17
https://www.wikipedia.org/page2.html 11 5 9 0 25
https://www.wikipedia.org/page3.html 1 4 3 0 8

where:

https://www.wikipedia.org/page2.html - start url
	Tesla - 11 hits
	Musk - 5 hits
	Gigafactory - 9 hits
	Elon Mask - 0 hits
	total inclusions - 25 hits

Requirements:

Maven 3.6.0
JDK 11.0.1

For program execution:
cd to project root folder in command line
mvn compile
mvn exec:java -Dexec.mainClass=test.chuvasova.webcrawler.CrawlerApp
