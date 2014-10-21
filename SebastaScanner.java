import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SebastaScanner {
	
	//class variables 
	static int charClass;
	//static char [] lexeme= new char[200];
	static String lexeme;
	//static String END="EOF";
	static char nextChar;
	static int token;
	static int lexlen;
	static int nextToken;
	static FileReader inputStream = null;
	
	//Token variables 
	static final int LETTER = 0;
	static final int DIGIT = 1;
	static final int UNKNOWN=99;
	static final int EOF=-1;
	static final int INT_LIT=10;
	static final int IDENT=11;
	static final int ASSIGN_OP=20;
	static final int ADD_OP=21;
	static final int SUB_OP=22;
	static final int MULT_OP=23;
	static final int DIV_OP=24;
	static final int LEFT_PAREN=25;
	static final int RIGHT_PAREN=26;
	
	
	
	//main function , open input data file and process its contents
	public static void main(String[] args) throws IOException {
		try{
			inputStream = new FileReader("input.txt");
			getChar();
			do
			{
				lex();
			}
			while(nextToken!=EOF);
		}
		
		catch(FileNotFoundException e)
		
		{
			System.out.println("File open error");
			System.out.println("Directory:"+System.getProperty("user.dir"));
		}
	
	finally{
		if(inputStream!=null)
		{
			try
			{
				inputStream.close();
			}
			catch(IOException e)
			{
				System.out.println("File close error");
			}
		}
	}
}
	
	/* addChar - a function to add nextChar to lexeme */	
public static void addChar()
{
	if (lexlen<=98)
	{
	
		lexeme=lexeme+ nextChar;
		lexlen++;
		}	
	else
	{
		System.out.println("Error : lexeme is too long");
	}
}
	

/* getChar - a function to get the next character of
input and determine its character class */
public static void getChar() throws IOException {
		 
		nextChar = (char) inputStream.read();
		if (nextChar!= EOF)
		{
		if (Character.isAlphabetic(nextChar)){
			charClass = LETTER;
		}
		else if (Character.isDigit(nextChar)){
			charClass = DIGIT;
		}
		else {
			charClass = UNKNOWN;
		}
		}
			
		else 
		{
		charClass = EOF;	
		lexeme="EOF";
		}
			}


/* getNonBlank - a function to call getChar until it
returns a non-whitespace character */
public static void getNonBlank() throws IOException
	{
		while (Character.isWhitespace(nextChar))
		{
		getChar();	
	}
	}


/* lookup - a function to lookup operators and parentheses
and return the token */
public static int lookup(char ch)
	{
		switch (ch){
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken=SUB_OP;
			break;
		case '*':
			addChar();
			nextToken=MULT_OP;
			break;
		case '/':
			addChar();
			nextToken=DIV_OP;
			break;
		default:
			addChar();
			nextToken=EOF;
			lexeme="EOF";
			break;
		}
	return nextToken;
	}


/* lex - a simple lexical analyzer for arithmetic
expressions */
public static int lex() throws IOException
{
	//lexlen=0;
	lexeme="";
	getNonBlank();
	switch (charClass){
	case LETTER:
		addChar();
		getChar();
		while (charClass == LETTER || charClass == DIGIT){
			addChar();
			getChar();
			
		}
		nextToken = IDENT;
		break;
	case DIGIT:
		addChar();
		getChar();
		while(charClass == DIGIT)
		{
			addChar();
			getChar();
		}
		nextToken=INT_LIT;
		break;
	case UNKNOWN:
		lookup(nextChar);
		getChar();
		break;
	case EOF:
		nextToken = EOF;
        //lexeme="EOF";
	     break; 
		
	}
System.out.println("next token is" + " " + nextToken + "    " + "next lexeme is" + "  " + lexeme);

return nextToken;
}
}

	
