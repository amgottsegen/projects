/*
*Author: Amy Gottsegen

Purpose: This program reads a text file and interprets the difficulty level based on several measures of readability
*/
import java.io.*;
import java.util.Scanner;
import java.lang.Math;
import java.awt.GridLayout;
import javax.swing.*;
   
   
public class DifficultyMachine
{
      /**
      takes text file and returns a string buffer
   	 @param pathname the text to be loaded into a StringBuffer
   	 @return the loaded StringBuffer
      */
   public static StringBuffer loadFile(String pathname)
   {
      try
      {
         File file = new File(pathname);
         StringBuffer strBuffer = new StringBuffer((int)file.length());
         BufferedReader input = new BufferedReader(new FileReader(file));
      	//the first character in the file
         int ch = 0;
         while((ch = input.read()) != -1)
         {
            strBuffer.append((char)ch);
         }
      
         input.close();
         return strBuffer;
      }
      //provision for nonexistant file
      catch (IOException ioe) 
      {
         StringBuffer str = new StringBuffer("Trouble reading from the file: " + ioe.getMessage());
         System.out.print(str);
         return str;
      } 
       
   }
   
   /**
   Counts the number of sentences
   @param sb the StringBuffer that contains the text from the given file
   @return the number of sentences ending in a period, colon, semicolon, question mark, or exclamation point
   **/
   public static int sentenceCount(StringBuffer sb)
   {
      //tracks location in the StringBuffer
      int n = 0;
      //counts the number of clause-terminating punctuation marks
      int count = 0;
      while(n != -1)
      {
         n = sb.indexOf("!",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      n=0;
      while (n != -1)
      {
         n=sb.indexOf(":",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      n=0;
      while (n != -1)
      {
         n=sb.indexOf(";",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      n=0;
      while (n != -1)
      {
         n=sb.indexOf(".",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      n=0;
      while (n != -1)
      {
         n=sb.indexOf("?",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      return count;
   }
   
   /**
   Counts the number of words
   @param sb the StringBuffer that contains the text from the given file
   @return the number of words
   **/
   public static int wordCount(StringBuffer sb)
   {
      //tracks location in the StringBuffer
      int n = 0;
      //counts the number of spaces and new lines
      int count = 0;
      while(n != -1)
      {
         n = sb.indexOf(" ",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      n=0;
      while (n != -1)
      {
         n=sb.indexOf("\n",n+1);
         if (n != -1)
         {
            count++;
         }
      }
      count++; //because white spaces seperate two words, the number of white spaces is 1 less than the number of words
      return count;
   }
   
   /**
   Tests if the current character is a vowel
   @param c the current character
   @return true or false
   **/
   public static boolean isVowel(char c)
   {
      if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y')
      {
         return true;
      }
      return false;
   }
   
   /**
   Counts the number of syllables
   @param sb the StringBuffer that contains the text from the given file
   @return the number of syllables
   **/
   public static int syllableCount(StringBuffer sb)
   {
      int count=0;
      for(int i=0;i<(sb.length());i++)
      {
         if(i != sb.length()-1)//prevent StringIndexOutofBounds exception
         {
            if(isVowel(sb.charAt(i)) )
            {
               if(sb.charAt(i) == 'e')
               { 
                  if(sb.charAt(i+1) == ' ' || sb.charAt(i+1) == '\n') //check if the current character is an 'e' at the end of a word, skip over it
                  {
                     i++;
                  }
                  else
                  {
                     count++;
                     try
                     {
                        while(isVowel(sb.charAt(i+1))) //prevent a group of vowels from being counted as individual syllables
                        {
                        
                           i++;
                        }
                     
                     }
                     catch (StringIndexOutOfBoundsException ibe)
                     {
                        return count;
                     }
                     
                  }
               }
               else
               {
                  count++;
               
                  try
                  {
                     while(isVowel(sb.charAt(i+1))) //prevent a group of vowels from being counted as individual syllables
                     {
                        
                        i++;
                     }
                     
                  }
                  catch (StringIndexOutOfBoundsException ibe)
                  {
                     return count;
                  }
               
               }
            }
         }
         else
         {
            if(isVowel(sb.charAt(i)) && sb.charAt(i) != 'e')
            {
               count++;
            }
         }
      }
      return count;
   }
   
   /**
   Counts the number of polysyllablic words
   @param sb the StringBuffer that contains the text from the given file
   @return the number of polysyllables
   **/
   public static int polysyllableCount(StringBuffer sb)
   {
      int polys = 0;
      //tracks location in the StringBuffer
      int n = 0;
      while(n<sb.length())
      
      {
         StringBuffer word = new StringBuffer("");
         //creates StringBuffer of the current word
         try
         {
            while (! Character.isWhitespace(sb.charAt(n)) )
            {
               word.append(sb.charAt(n));
               n++;
            }
         }
         catch(StringIndexOutOfBoundsException ibe)
         {
         }
         
         n++;
         int syllables = syllableCount(word);
         //if (syllables >= 3)
         {
            polys++;
         }
      }
      return polys;
   }
   
   /**
   Calculates the Flesch Reading Ease Score
   @param sb the text file to be evaluated
   @return the score
   **/
   public static double findFRES(StringBuffer sb)
   {
      double score = 206.835 - 84.6 * (syllableCount(sb)) / (wordCount(sb)) - 1.015 * (wordCount(sb)) / (sentenceCount(sb));
      return score;
   }
   
   /**
   Interprets the Flesch Reading Ease Score to give an grade level
   @param sb the text file to be evaluated
   @return the interpretation
   **/
   public static String interpretFRES(StringBuffer sb)
   {
      double score = findFRES(sb);
      if(score < 0)
      {
         return "Law School Graduate";
      }
      else if(score<30)
      {
         return "College Graduate";
      }
      else if(score<50)
      {
         return "College Student";
      }
      else if(score<60)
      {
         return "High School Student";
      }
      else if(score<65)
      {
         return "Ninth Grader";
      }
      else if(score<70)
      {
         return "Eighth Grader";
      }
      else if(score<80)
      {
         return "Seventh Grader";
      }
      else if(score<90)
      {
         return "Sixth Grader";
      }
      else
      {
         return "Fifth Grader";
      }
   }
   
   /**
   Calculates the Flesch-Kincaid Reading Age
   @param sb the text file to be evaluated
   @return the score
   **/
   public static Double findFKRA(StringBuffer sb)
   {
      Double score = 11.8* syllableCount(sb) / wordCount(sb) + 0.39 * wordCount(sb) / sentenceCount(sb) - 15.59;
      return score;
   }
   
   /**
   Calculates the Simple Measure of Gobbledygook Reading Indicator 
   @param sb the text file to be evaluated
   @return the score
   **/
   public static double findSMOGindicator(StringBuffer sb)
   {
      double score = polysyllableCount(sb) / sentenceCount(sb) * 30;
      return score;
   }
   
   /**
   Interprets the Simple Measure of Gobbledygook Reading Indicator into a grade level
   @param sb the text file to be evaluated
   @return the grade level
   **/
   public static int findSMOG(StringBuffer sb)
   {
      int score = 0;
      Double root = Math.sqrt(findSMOGindicator(sb));
      int smallRoot = root.intValue();
      int bigRoot = root.intValue() + 1;
      if((root - smallRoot) < (bigRoot - root))
      {
         score = smallRoot + 3;
      }
      else
      {
         score = bigRoot +3;
      }
      return score;
   }
   
   /**
   Collects the users input for the text file name, or ends the run
   @param scanner the scanner for reading the input
   @return the user's input
   **/
   public static String lineRead(Scanner scanner)
   {
      String name = scanner.nextLine();
      if(name.equals("QUIT"))
      {
         System.exit(1);
      }
      return name;
   }
   
   public static void main(String[] args) throws IOException
   {
      Scanner scanner = new Scanner(System.in);
      StringBuffer sb = new StringBuffer("");
      String name = "";
      PrintWriter out = new PrintWriter(new FileWriter("output.txt",true)); 
    
      while (true)
      {
         /**
         Repeats until the user enters a valid file name or ends the run
         **/
         do
         {
            System.out.print("\n Enter file name (or QUIT to exit): ");
            name = lineRead(scanner);
            sb = loadFile(name);
         } while (sb.substring(0,30).equals("Trouble reading from the file:"));
        
         /**
         Prints the various evaluations of the text
         **/
         System.out.print("The file " + name + " has Flesch Reading Ease Score index of " + findFRES(sb) + ". \n");
         System.out.print("It requires the reading level of a " + interpretFRES(sb) + " to read. \n");
         System.out.print("Its Flesch-Kincaid Grade Level Formula value is " + findFKRA(sb) + " , indicating \n");
         System.out.print("it is at a " + findFKRA(sb).intValue() + " grade level. \n");
         System.out.print("Its SMOG Readabilty Formula indicator is " + findSMOGindicator(sb) + " , indicating it is \n");
         System.out.print("at a " + findSMOG(sb) + " grade level. \n \n");
         
         //Prints a summary of the evaluations to a text file, "output.txt"
         out.println(name + "\t" + sentenceCount(sb) + "\t" + wordCount(sb) + "\t" + syllableCount(sb)+ "\t" + polysyllableCount(sb) + "\t" + findFRES(sb) + "\t" + findFKRA(sb) + "\t" + findSMOG(sb)); 
         out.close();
      }
   }
}