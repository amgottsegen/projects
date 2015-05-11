//Author: Amy Gottsegen
//Date: 2-28-15

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include "Grid.h"

using namespace std;

int main(void)
{
	
	Grid g(18);
	ofstream out("output.txt");
	if (placeDivas(g,0))
	{
		cout << g;
		if (out.is_open())
			out << g;
	}
	return 0;
}

