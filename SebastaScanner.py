import sys
global INT_LIT,LETTER,DIGIT,IDENT,ASSIGN_OP,ADD_OP,SUB_OP,MULT_OP,DIV_OP,LEFT_PAREN,RIGHT_PAREN,UNKNOWN
ADD_OP=21
IDENT=11
SUB_OP=22
MULT_OP=23
INT_LIT=10
LETTER=0
DIGIT=1
ASSIGN_OP=20
DIV_OP=24
LEFT_PAREN=25
RIGHT_PAREN=26
UNKNOWN=99
EOF=-1
nextToken=0
nextChar=""
charClass=0
lexlen=0
lexeme=""


'''addChar - a function to add nextChar to lexeme '''
def addChar():
        global lexeme
        global nextChar
        global lexlen
        #lexeme = ""
        if lexlen <= 98:
            lexeme = lexeme+nextChar
            lexlen = lexlen+1
        else:
            print "Error:lexeme is too long"
            
''' getChar - a function to get the next character of
input and determine its character class '''
def getChar():
        global nextChar
        global charClass
        global EOF
        nextChar=in_fp.read(1)
        if nextChar!="NULL":
            if nextChar.isalpha():
               charClass=LETTER
            elif nextChar.isdigit():
               charClass=DIGIT
            else:
               charClass=UNKNOWN
        else:
            charClass=EOF
            lexeme =lexeme+"EOF"
            return lexeme



''' lookup - a function to lookup operators and parentheses
and return the token '''       
def lookup(ch):
        global nextToken
        global UNKNOWN
        global lexeme
        if(ch=='('):
            addChar()
            nextToken=LEFT_PAREN
            
        elif(ch==')'):
            addChar()
            nextToken=RIGHT_PAREN
            
        elif(ch=='+'):
            addChar()
            nextToken=ADD_OP
            
        elif(ch=='-'):
            addChar()
            nextToken=SUB_OP
            
        elif(ch=='*'):
            addChar()
            nextToken=MULT_OP
            
        elif(ch=='/'):
            addChar()
            nextToken=DIV_OP
            
        else:
            addChar()
            nextToken=EOF
            lexeme = lexeme+"EOF"
            
        return nextToken

''' getNonBlank - a function to call getChar until it
returns a non-whitespace character '''           
def getNonBlank():
        global nextChar
        while nextChar.isspace():
            getChar()
            
'''lex - a simple lexical analyzer for arithmetic
expressions '''   
def  lex():
        global lexeme
        global nextToken
        global charClass
        lexeme = ""
        getNonBlank()
        if charClass==LETTER:
            addChar()
            getChar()
            while charClass == LETTER or charClass==DIGIT:
                addChar()
                getChar()
                
            nextToken = IDENT
            
        elif(charClass==DIGIT):
            addChar()
            getChar()
            while(charClass==DIGIT):
             addChar()
             getChar()
            nextToken = INT_LIT
            
        elif(charClass==UNKNOWN):
            nextToken = lookup(nextChar)
            getChar()
           
        else:
            nextToken = EOF
    
        print "  next token is  " + str(nextToken) + "  next lexeme is  " + "  " +lexeme
        return nextToken
    
    


if __name__ == '__main__':
    global in_fp
    parse=0
    try:
      in_fp = open('input.txt','r')
      if in_fp != EOF :
             parse=1
      else :
            print 'Usage:\n Python input.txt[file-to-parse(optional,default=input.txt)]'
    except :
        print "expected error:",sys.exc_info()[0]
        raise
    if parse:
        getChar()
        lex()
        while nextToken != EOF :
          lex()
        in_fp.close()
