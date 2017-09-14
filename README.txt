1) For each of the Design Decisions mentioned above, please discuss possible options that you considered, what you ended up choosing, and why. 
	
	I chose the starting capacity of 11 for my TextAssociator. Though this may be small for the simple_thesaurus, this works great. 
	This is because 11 is a prime number close to the size of simple_thesaurus (8). 11 being a prime number means each indicie for HashTable 
	has a possiblity of being used (no indicie will be perpetually skipped since modulus of prime will almost always get a remainder for the index).
	I chose a load factor of 1.5. This is because with seperate chainging it is very much possible to get multiple values to have the same indicie.
	Since resizing a costly function to run (about O(n^2) in my case not including the hashcode function) it important to not run it as often. 
	It is also important to not have to go through long WordInfoSeperateChain objects for any index, since functions like getAssociations will run 
	closer to O(n) instead of O(1) if the load factor is too high (meaning that many more values are allowed in a HashTable than compared to the table
	size. Because of these reasons 1.5 is the right medium ground that allows not having to resize too often, and not having too long WordInfoSeperateChains.
	When I resize my table, I use use a function 2*P + 1 that produces primes only (assuming P is a prime which P=11 is). This is similar to doubling, which
	means that resize wont have to be called as often since reach the load factor capacity will take longer, and since this new size is prime it will have 
	all the benefits of a prime number  (no indicie will be perpetually skipped since modulus of prime will almost always get a remainder for the index).
	
2) What hash function did you choose for your TextAssociator (i.e. did you did you use String’s hashcode method, did you make your own)? Why was this hash function effective? Are there alternative hash functions that you considered? 
	
	The hash function that I decided to use in the TextAssociator is the inbuilt java hashcode for Strings. This function, upon further inspection, calculated an
	int value based on each induvidual character with in each string. This means it takes into account every differentiating variable within each string to produce
	a hash value. Because of this the possiblity of having the same hash value is minimal so the posibility of a collision is less. I was considering making my own
	hash function but because my function would be somewhat similar in its approach into making a hash value, there would be no need to create a new function that
	does something similar.

3) We chose to implement this TextAssociator with separate chaining. If you were instead going to use a different collision resolution scheme, what would you choose? How and where would your code change? Give several specific examples to illustrate your understanding.
 	
	If we were to use a different collision resolution it would be linear probing. The loading factor will have to be less than one (realistically around .75). Since
	linear probbing hash tables can only use have as many input values as indicies in the table. Whenever there is a collision it is important this class tries a new hash value
	like hash value + 1 so that a new indicie is produced that does form a collision. It is also imperative to prime numbers because you dont want any indicie being skipped
	since the modulus of the table size produces a values that could never be that indicie that is skipped (but that won't happen if the table size is prime). It is important to resize at load fact around .75 
	instead of 1 because you dont want to keeping probing for a new indicie in the table that is not taken because even though this it is costly to resize it is close to O(n) to probe through
	the table if the table is almost full and each hash value keeps producing collisions.
	
4) How long did you spend on this assignment? What portion did you find most/least challenging?
	
	I took about 4.5 hours with write up for the whole assignment. The most challenging part was wrapping my head arround why we produce hash values as apposed to 
	putting each object in order. The least Challenging part was making a client code since there was already an example.

My favorite sentence I got after I ran ThesaurusClient:
"my gift of tongues is clearly enough and I am slightly on the"