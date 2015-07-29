# DocxSearcher
Version 0.5, this is still in development, but it's a good working version with plenty of improvements still to come.
At this point I have not created the interface API yet, but this will be in place for V1.0.

This library allows a user to define a set of searches to be performed on docx files.

The final object will contain the following
1. The original text.
2. The text with mark-up tags surrounding the found text.
3. A data structure containing the name of each search and the positions of the first and last
 character what the match was found.

It works in a similar way to regex where by the search is defined in a particular syntax and then the search is performed.
The user will define a set of searches in an xml file, new searches can be defined on the fly and then read in at run time,
it also allows the user to build a set of predefined searches for future use.
Another feature (IMHO) of this solution is that the searches can be defined in a very simplistic way
that allows users with a basic knowledge of xml to become proficient quickly without having to become an expert in regex.

The searches are defined in an xml file and Jaxb is used to read the xml and load the sequence of searches ready
for processing, the xml that defines what each search looks like is determined by an xsd schema file.

Dox4j is used to read the docx file and provide the file contents paragraph by paragraph.	

Example of one search within a sequence file:
	
	<p:sequence p:name="EXAMPLE" p:id="AnExample" p:action="CONTINUOUS">
		<p:piece p:recurrence="SINGLE">
			<p:bit p:type="OPEN_BRACKET" p:action="SKIP" />
			<p:bit p:type="LOW_ALPHA" p:action="STEP" />
			<p:bit p:type="CLOSE_BRACKET" p:action="SKIP" />
		</p:piece>
	</p:sequence>

The above search will scan each line of text contained within a given docx file, and try to recognise
the sequence defined in the xml. The basic format to define a search has three parts "p:sequence"
which contains one or several "p:piece" elements and in turn each "p:piece" is made up of one or several
"p:bit" elements.

(Only the contents of the listed search are discussed, complete explanation will be given in v1.0) 

"p:sequence" has three attributes p:type, p:id and p:action.
  * p:name, is used as the value in the tag name that surrounds the found text.  
  * p:id, only used internally for the moment. 
  * p:action, this can contain one of two values, "SINGLE" or "CONTINUOUS"

"p:piece" has one attributes p:recurrence. (This is for implementation in v1.0)

"p:bit" has two attributes p:type and p:action.
  * p:type, can have one of multiple values from the following list; 	"ANY", "CAP_ALPHA", "LOW_ALPHA", "ALPHA",
			"NUMERIC", "FULL_STOP",	"COMMA", "SPACE",	"OPEN_TAG", "CLOSE_TAG_SLASH", "CLOSE_TAG",	"BOLD",	"ITALIC",
			"OPEN_BRACKET",	"CLOSE_BRACKET", "OPEN_QUOTE", "CLOSE_QUOTE",	"COLON", "SEMI_COLON", "LAST_CHAR",	"MULTI", "EXACT".
			(This list will grow before v1.0)
  * p:action, one of three values, "STEP", "SKIP" or "EXACT".

In this example the search will start at the beginning of the line and match each piece of the sequence,
until the end of line is reached.	A piece is made up of bits, the first bit above based on its action
attribute which in this case is "SKIP". "SKIP" tells the code to compare against each character until
it finds the matching character defined in the p:type attribute which in this case it's "OPEN_BRACKET",
once it has matched the "OPEN_BRACKET" the code will move on to the next bit, the next bit's action attribute is "STEP",
what this tells the search to do is, from the cursor position where the previous bit "OPEN_BRACKET" was matched
look at the next character, if the next character matches what's defined in the p:type attribute which the current 
bit is defined as "LOW_ALPHA" then we have a match so move onto the next bit in the piece. So now that we have found
matches for the first and second bits, search for the third bit, this bit has a type attribute of "CLOSE_BRACKET" and
an action attribute of "SKIP", this will now look at each of the remaining characters until it finds a match or no
match is found. Phew!
	
(Version 1.0 will	contain a help file with full details of all available sequence file options)


Check out the project to eclipse, build and run it.
Running main will be prompt you to select two files firstly a "docx" file, select the file "Example.docx" in the resources folder,
then select the sequence file "Example.xml" also located in the resources folder.

The program will run and the following will appear in the cosole.

You chose to search this file: C:\Users\dell\workspace\DocxSearcher\src\resources\Example.docx
With this sequence file: C:\Users\dell\workspace\DocxSearcher\src\resources\Example.xml

----------------------------- The document before -----------------------------
```
Duplicate (ref 1234) the (ref 4567).
Duplicate (ref 1234) the (ref 4567).
in (ref 1111) the (ref 2222) beginning (ref 3333) there was a lonely sentence that began with a lower case letter.
The title is THE TITLE, by Derek Keogh.
and then there was an invalid sentence.
1 more invalid sentence.
The rest is (ref hello) and (this is a ref from 1990).
```
----------------------------------- and after -----------------------------------
```
Duplicate <EXAMPLE>(ref 1234)</EXAMPLE> the <EXAMPLE>(ref 4567)</EXAMPLE>.
Duplicate <EXAMPLE>(ref 1234)</EXAMPLE> the <EXAMPLE>(ref 4567)</EXAMPLE>.
<FOUR>in <EXAMPLE>(ref 1111)</EXAMPLE> the <EXAMPLE>(ref 2222)</EXAMPLE> beginning <EXAMPLE>(ref 3333)</EXAMPLE> there was a lonely sentence that began with a lower case letter.</FOUR>
<TWO>The title is THE TITLE,</TWO> by<ONE> Derek Keogh.</ONE>
<FOUR>and then there was an invalid sentence.</FOUR>
<FOUR>1 more invalid sentence.</FOUR>
<TWO>The rest is <EXAMPLE>(ref hello)</EXAMPLE> and <EXAMPLE>(this is a ref from 1990)</EXAMPLE>.</TWO>
```
