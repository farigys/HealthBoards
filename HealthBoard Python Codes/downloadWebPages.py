'''
created by Farig Sadeque

'''
import mechanize, re, os, sys, shutil
from time import sleep
from urllib2 import URLError
from BeautifulSoup import *


# ## find all pages of a thread and put them in the dictionary sent as parameter
def findSubPages(link, html, subURLDict):
	#print html
	soup = BeautifulSoup(html)
	discussion = soup.findAll('td', {"class":"vbmenu_control","style":"font-weight:normal"})
	if len(discussion) != 0:
		maxmPage = int(discussion[0].string.split()[3])
		newLink = link.split('.')[0] + "." + link.split('.')[1] + "." + link.split('.')[2]	

		for l in range(1,maxmPage):
			filename = newLink + "-" + str(l+1) + ".html"
			subURLDict[filename] = filename
	
		#print discussion
	
		 
            
            
            
            
# ## download all the discussion forums
def download_forums(link, dirName):
	br = mechanize.Browser()
	br.set_handle_robots(False)
	br.set_handle_equiv(False)

	# # first download the main file and write to disk
	#print link, " has started downloading"

	#print "Download started"	
	
	try:
		
		### first download the file itself. 
		br.open(link)
		html = br.response().read()
		f = open(dirName + "/" + getFileName(link), "w")  
		f.write(html)
		f.close()
		#print link , " has completed downloading"
		subURLDict = {}
		### if the given thread has other pages, i.e. if the number of replies is more than some value, the same thread will have multiple pages. 
		###so, findsubpages will find the subpages of the given thread
		findSubPages(link, html, subURLDict)
		### now start downloading the subpages of the given thread
		
		for temp in subURLDict:
			try:
				#print temp , " has started downloading"
				# print temp, subURLDict[temp] 
				  
				br.open(subURLDict[temp])
				html = br.response().read()
				tempName = getFileName(temp)
				f = open(dirName + "/" + tempName, "w")
				f.write(html)
				f.close()
				#print temp , " has completed downloading"
			except URLError,e:
				print ("Download error while downloading " + tempName + ", retrying in a few seconds: " + str(e) )
			sleep(5)			
	except URLError,e:
		print ("Download error while downloading " + getFileName(link) + ", retrying in a few seconds: " + str(e) )
	sleep(5)        



def getFileName(url):
	return url.split('/')[5]


### returns the array of URLs of all threads present in the given file
def read_total_list(inputFile):

    # read total fetch list csv
    total_list = [line.strip() for line in open(inputFile)]
    return total_list

def valid_XML_char_ordinal(i):    
    return (# conditions ordered by presumed frequency
        0x20 <= i <= 0xD7FF 
        or i in (0x9, 0xA, 0xD)
        or 0xE000 <= i <= 0xFFFD
        or 0x10000 <= i <= 0x10FFFF
        )


### reads the file in given directory, and removes those files from the fetch_list and returns back the remaining list which are not yet downloaded
def purge_already_fetched(fetch_list, outputDir):

    # list of threads that still need downloading
    rem_list = []
    # check each tweet to see if we have it
    for item in fetch_list:
	thread_file = outputDir + '/' + item.rpartition('/')[2] + ".html"
	if(os.path.exists(thread_file)):
		subURLDict = {}
		f = open(thread_file, "r")
		content = f.readlines()
		html = ""
		for c in content:
			for l in c:
				if valid_XML_char_ordinal(ord(l)):
					html = html + l 
                    
		# print html
		findSubPages(html, subURLDict)
		for page in subURLDict:
			# print "inside suburldict"
			outputPath = outputDir + '/' + getFileName(subURLDict[page])
			if not os.path.exists(outputPath):
				rem_list.append(item)
				print "if not exists" 
				break
	else:
		rem_list.append(item)

    return rem_list






def get_user_params():

    user_params = {}
    # get user input params
    board = str(sys.argv[1])
    user_params['outputDir'] = board
    user_params['filesList'] = 'FilesLists.txt'

    
    if user_params['filesList'] == '' or user_params['outputDir'] == '':
		print "Invalid file containing list of threads or Output directory. Try again"
		sys.exit()   

    return user_params



def dump_user_params(user_params):

    # dump user params for confirmation
    print 'File containing list of threads:    ' + user_params['filesList']
    print 'Output Directory:   ' + user_params['outputDir']
    return


        
def main():
	user_params = get_user_params()
	dump_user_params(user_params)
	outputDir = user_params['outputDir'] + "/HTML"
	print "html files will be stored in " + outputDir
	
	
	### get all the URLs listed in the input FilesLists file
	total_list = read_total_list(user_params['outputDir'] + '/' + user_params['filesList'])
	## if the HTML directory already exists, then purge_already_fetched will read the files in the HTML directory 
	###and removes those URLs present in the HTML directory from the to_download array
	### else all the threads have to be downloaded
	#if os.path.exists(outputDir):
	#	fetch_list = purge_already_fetched(total_list, outputDir)
	#else:
	os.makedirs(outputDir)
	fetch_list = total_list
	print "Total threads: " + str(len(total_list))
	print "Threads remaining to download: " + str(len(fetch_list))
	
	
	## start downloading each remaining threads.
	for i in range(0, len(fetch_list)):
		if(i%50 == 0):
			print str(i+1) + " threads done"		
		download_forums(fetch_list[i], outputDir)


	
if(__name__ == '__main__'):
	main()	        
  
