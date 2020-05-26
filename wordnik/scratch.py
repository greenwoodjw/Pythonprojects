
import json
import requests
import sys, getopt  # args
import smtp3

# /input args

#import sys, getopt


def main(argv):
    verbose = 'false'
    inputfile = '/Users/jgreenwood/input.txt'
    outputfile = '/Users/jgreenwood/output4.txt'
    try:
        opts, args = getopt.getopt(argv, "vhi:o:", ["ifile=", "ofile="])  #: has value, no colon no value
    except getopt.GetoptError:
        print('test.py -i <inputfile> -o <outputfile> -v')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('test.py -i <inputfile> -o <outputfile>')
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-o", "--ofile"):
            outputfile = arg
        elif opt == '-v':
            verbose = 'true'
    print('Input file is ', inputfile)
    print('Output file is ', outputfile)
    # end args

    word_list = []
    # get1 = "https://api.wordnik.com/v4/word.json/"
    # get2 = "/definitions?limit=5&includeRelated=false&useCanonical=false&includeTags=true&api_key=8144de29a60b18189680e0409f109da6f38c2939476a9f4c5"
    # get3 = ""

    fp = open(inputfile, 'r')
    filepath = inputfile
    with open(filepath) as fp:
        for cnt, line in enumerate(fp):
            if verbose == 'true':
                print("Line {}: {}".format(cnt, line))
            word_list.append(line)
    with open(outputfile, "a") as myfile:
        for w in word_list:
            url = "https://api.wordnik.com/v4/word.json/" + w.strip() + "/definitions?limit=5&includeRelated=false&useCanonical=false&includeTags=true&api_key=8144de29a60b18189680e0409f109da6f38c2939476a9f4c5"
            response = requests.get(url)
            print("\n"+url)
            print(response)
            todos = json.loads(response.text)
            if len(todos) == 0:
                if verbose == 'true':
                    print("\n" + w.strip() + " - foo, no definition")
                myfile.write("\n" + w.strip() + " - foo, no definition" + "\n")

            for i in range(len(todos)):

                try:
                    wordKey=todos[i]['word']
                except KeyError:
                    wordKey=""
                try:
                    partOfSpeechKey=todos[i]['partOfSpeech']+", "
                except KeyError:
                    partOfSpeechKey=""
                try:
                    textKey=todos[i]['text']
                    textKey=textKey.replace("<xref>","")
                    textKey=textKey.replace("</xref>","")
                    textKey=textKey.replace("<xref urlencoded=","")
                    textKey=textKey.replace("<internalXref urlencoded=","")
                    #textKey=textKey.replace("<internalXref>","")
                    textKey=textKey.replace("</internalXref>","")
                    textKey=textKey.replace("encodedurl=","")
                except KeyError:
                    textKey=""

                if i == 0:
                    if verbose == 'true':
                        #print("\n" + todos[i]['word'] + " - " + todos[i]['partOfSpeech'] + " , " + todos[i]['text'])
                        print("\n" + wordKey + " - " + partOfSpeechKey  + textKey)
                    myfile.write(
                        #"\n" + todos[i]['word'].encode('utf-8') + " - " + todos[i]['partOfSpeech'].encode('utf-8') + " , " + todos[i]['text'].encode('utf-8') + "\n")
                        #"\n" + todos[i]['word'] + " - " + todos[i]['partOfSpeech'] + " , " + todos[i]['text'] + "\n")
                        "\n" + wordKey + " - " + partOfSpeechKey  + textKey + "\n")
                else:
                    if verbose == 'true':
                        print("\t  - " + partOfSpeechKey  + textKey)
                    #myfile.write("\t  - " + todos[i]['partOfSpeech'].encode('utf-8') + " , " + todos[i]['text'].encode('utf-8') + "\n")
                    myfile.write("\t  - " + partOfSpeechKey  + textKey + "\n")


if __name__ == "__main__":
    main(sys.argv[1:])
    smtp3.sendList()

# //end args
