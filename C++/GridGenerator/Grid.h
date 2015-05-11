//----------------------------------------------------------------------------
//This header file was generated with Amy Gottsegen's Class Wizard, v. 1.00, last modified 2-18-15

// Grid Class
// Header 
// This is a grid of tables at the academy awards baquet dinner
//----------------------------------------------------------------------------
// Author : Amy Gottsegen
// Date : 2-28-15
//----------------------------------------------------------------------------

#ifndef _Grid_h_
#define _Grid_h_

#include <string>
#include <iostream>
#include <vector>
using namespace std;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

class Grid
{ 
public: 
//------------------------------------------------------
//----- Constructors -----------------------------------
//------------------------------------------------------

Grid(); 
Grid(int n);

//------------------------------------------------------
//----- Destructors ------------------------------------
//------------------------------------------------------

~Grid();

//------------------------------------------------------
//----- Inspectors ------------------------------------
//------------------------------------------------------

int getSize() const;

//------------------------------------------------------
//--------------Overloaded Operator--------------------
//------------------------------------------------------
void output(ostream &out) const;

vector<int> & operator[](int row);

private: 
	int n_;
	vector< vector<int> > board_;
};


//------------------------------------------------------
//----- Facilitators ---------------------------------------
//------------------------------------------------------

bool safeSpot(int m, int n, Grid & g);
bool placeDivas(Grid& board, int row);

//--------------Overloaded Operator-----------------
ostream& operator<< (ostream& out, Grid &g);
#endif