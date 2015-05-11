/**
Author: Amy Gottsegen
Date: 11/25/14
**/
var permArr = [],
usedChars = [];

//tests if the user's word is in the dictionary
function catchFakeWord(string)
{
	if(! document.getElementById("result").value)
	{
		document.getElementById("result").value += ("Our dictionary has identified a nonsense word! Here are its nonsense anagrams: " + "\n")
		permute(string.split(""))
		for(j=0;j<permArr.length;j++)
		{
			document.getElementById("result").value += (permArr[j].join("") + "\n")
		}
	}
}

//returns all the permutations of the users word
function permute(input)
{
	var i, ch
    for (i = 0; i < input.length; i++) 
	{
        ch = input.splice(i, 1)[0];
        usedChars.push(ch);
        if (input.length == 0) 
		{
            permArr.push(usedChars.slice());
        }
        permute(input);
        input.splice(i, 0, ch);
        usedChars.pop();
    }
    return permArr
}