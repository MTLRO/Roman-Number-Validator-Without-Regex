When I started this project, I did not know what were regular expressions in coding. I used Arrays and ArrayLists and simple java methods to iterate through a string passed as input. 

If one of the following conditions where met, the method would automatically return false:

- Empty String 
- If these special cases occured:
  - XL or XC is preceded by a character other than C, M, D and was not at the beginning of the string.
  - CD or CM is preceded by a character other than M and was not at the beginning of the string.
  - the patterns IVI,IXI,XLX,XCX,CDC,CMC occur.
  - three I's in a row but the string does not end after that.
  - IV or IX not occuring at the end of the string.
  - a letter appearing more than 4 times.
- If certain letters appear after a specific letter:
  - L,C,D or M after I.
  - D or M after X.
  - any letter except I after V.
  - after the combination XC, the letters C,D,M or L.
  - L,C,D or M after L.
  - D or M after D.
I really think these conditions can be reduced, i.e having less but more robust conditions.

Basically, at each letter, these conditions are tested (method: validatorHelper). There is an array contained "banned letters", and an array that contains the counts for each letter
and an extra slot containing -1. This slot has index 7.
The helper function returns 7 if a condition is met (i.e. the number is invalid), or the appropriate index in the counts array depending on what letter is checked.
This way, after each letter, the count is increased.

The main function simply iterates through the string and applies this helper function until an invalidating condition is met or the end of the string is reached.

    
