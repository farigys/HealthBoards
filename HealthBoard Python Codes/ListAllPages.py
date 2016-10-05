'''
created by Farig Sadeque
'''
import mechanize, os, shutil, sys
from time import sleep
from BeautifulSoup import *




# ## this method will lists all the threads present in a seed URL and puts it into a dictionary and returns back.
# ## example: normally a seedURL contains 20 threads. it will list all the threads and puts the urls to the threads into the dictionary
def findAllThreads(seedUrl, threads):
    br = mechanize.Browser()
    br.set_handle_robots(False)
    br.set_handle_equiv(False)
    print "crawling link: " + seedUrl
    br.open(seedUrl)
    for l in br.links():
        newUrl = l.url  
             
        # ## populating all forum links
        if(re.search('http://www.healthboards.com/boards/.*/[0-9]+.*', newUrl)):
		print newUrl             
		threads.append(newUrl)
               

    # return threads

# ## this method will return the highest number of pages present in the given seed URL. Eg. the bone cancer forum contains 4 pages. So, it will return 4
def findHighestPageNumber(seedUrl):
    br = mechanize.Browser()
    br.set_handle_robots(False)
    print "seed url : " + seedUrl
    br.open(seedUrl)
    html = br.response().read()
    soup = BeautifulSoup(html)
    mainBody = soup.findAll('td', {"class":"vbmenu_control","style":"font-weight:normal"})
    maxmPage = int(mainBody[0].string.split()[3])

    return maxmPage
    
            
def my_range(start, end, step):
    while start <= end:
        yield start
        start += step



## this method writes the urls of the threads to the file. 
def writeAndCrawlAllThreads(crawledPagesFile, crawledThreadsFile, minPage, maxmPage, seedUrl, crawledThreadsDict):
	#print len(crawledThreadsDict)
	#print minPage, maxmPage
    
    # ## starting from minimum page to maximum page, it will crawl all the threads
	for j in range(minPage, maxmPage + 1):
		newUrl = seedUrl + "index" + str(j)+".html"
		# print seedUrl
		threads = []
		findAllThreads(newUrl, threads)
		for i in range(len(threads)):
			if threads[i] not in crawledThreadsDict:
				crawledThreadsFile.write(threads[i] + "\n")
		crawledPagesFile.write(newUrl + "\n")
		print "page " + newUrl + " completed"
		sleep(5) 






def get_user_params():

    user_params = {}
    # get user input params
    #user_params['seedUrl'] = raw_input('Seed URL: ')
    #user_params['outputDir'] = raw_input('\nOutput Directory: ')
    board = str(sys.argv[1])
    user_params['seedUrl'] = 'http://www.healthboards.com/boards/' + board + '/'
    user_params['outputDir'] = board
    
    if user_params['seedUrl'] == '' or user_params['outputDir'] == '':
		print "Invalid seed URL or Output directory. Try again"
		sys.exit()   
    return user_params



def dump_user_params(user_params):

    # dump user params for confirmation
    print 'Seed URL:    ' + user_params['seedUrl']
    print 'Output Directory:   ' + user_params['outputDir']
    return




        
def main():
    
	user_params = get_user_params()
	dump_user_params(user_params)
    # this program first finds the highest number of page in the given url
	maxmPage = findHighestPageNumber(user_params['seedUrl'])
	minPage = 1
    # # create the output directory if it doesn't exist
	if not os.path.exists(user_params['outputDir']):
		os.makedirs(user_params['outputDir'])

    # # this file is to list all the pages whose threads are crawled.
    # # e.g., if all the 20 threads in page http://www.dailystrength.org/c/Bone-Cancer/forum/page-2 are written to 
	allPagesFileName = user_params['outputDir'] + "/PagesCrawled.txt"
    # ## this file is to list all the threads
	allThreadsFilesName = user_params['outputDir'] + "/FilesLists.txt"
	crawledThreadsDict = {}
	if(os.path.exists(allPagesFileName)):
		crawledPagesFile = open(allPagesFileName, "r")
		allPages = crawledPagesFile.readlines()
		print(len(allPages))
        # ## this is to find the last page that is crawled before. 
        # ## for example: in the previous run, the threads upto page 2 are listed, then in this run, it will start crawling from page 3
		if(len(allPages) > 1):
			lastPage = allPages[len(allPages) - 1]
			parts = lastPage.rsplit('/', 1)
			m = re.search('page-([0-9]*)', parts[1])
			minPage = int(m.group(1)) + 1
			
		elif(len(allPages) == 1 and allPages[0].strip() != ""):
			minPage = 2
		crawledThreadsFile = open(allThreadsFilesName, "r")
		allThreads = crawledThreadsFile.readlines()
        # ## read the FilesLists.txt file and puts in dictionary saying these threads are already listed. 
		for r in range(len(allThreads)):
			crawledThreadsDict[allThreads[r].strip("\n")] = 1
		crawledPagesFile = open(allPagesFileName, "a")
		crawledThreadsFile = open(allThreadsFilesName, "a")
		# ## this method now lists the remaining threads into the file FilesLists.txt
		writeAndCrawlAllThreads(crawledPagesFile, crawledThreadsFile, minPage, maxmPage, user_params['seedUrl'], crawledThreadsDict)
		
		
		
	else:	
		crawledPagesFile = open(allPagesFileName, "w")
		crawledThreadsFile = open(allThreadsFilesName, "w")
		writeAndCrawlAllThreads(crawledPagesFile, crawledThreadsFile, minPage, maxmPage, user_params['seedUrl'], crawledThreadsDict)
	crawledPagesFile.close()
	crawledThreadsFile.close()
        
if __name__ == "__main__":    
    main()
