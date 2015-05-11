//----------------------------------------------------------------------------
// Grid Class
// Implementation 
// This is a grid of tables at the academy awards baquet dinner
//----------------------------------------------------------------------------
// Author : Amy Gottsegen
// Date : 2-28-15
//----------------------------------------------------------------------------

#include "Grid.h"
#include <string>
#include <iostream>
#include <vector>
using namespace std;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

//------------------------------------------------------
//----- Constructors -----------------------------------
//------------------------------------------------------

Grid::Grid() 
{ }

Grid::Grid(int n) {
	n_ = n;
	for (int i = 0; i < n; i++)
	{
		vector<int> v(n, 0);
		board_.push_back(v);
	}
}

//------------------------------------------------------
//----- Destructors ------------------------------------
//------------------------------------------------------

Grid::~Grid()
{ }

//------------------------------------------------------
//----- Inspectors ------------------------------------
//------------------------------------------------------

int Grid::getSize() const {
	return n_;
}


//------------------------------------------------------
//----- Overloaded Operators--------------------------
//------------------------------------------------------

vector<int> & Grid::operator[](int row)
{
	return board_[row];
}

ostream& operator<< (ostream& out, Grid &g) {
	for (int i = 0; i < g.getSize(); i++)
	{
		for (int j = 0; j < g.getSize(); j++)
		{
			out << g[i][j] << " ";
		}
		out << endl;
	}
return out;
}

//------------------------------------------------
//--------------Facilitators----------------------
//------------------------------------------------
/**
Checks if the spot (m,n) is a safe place for a diva
@param m the row
@param n the column
@param g the board
*/
bool safeSpot(int m, int n, Grid &g)
{
	int up = m;
	int across = n;
	//check if other divas in column
	for (int i = 0; i < g.getSize(); i++)
	{
		if (g[i][n] == 1)
			return false;
	}
	//check if other divas in row
	for (int i = 0; i < g.getSize(); i++)
	{
		if (g[m][i] == 1)
			return false;
	}
	//check if other divas on diagonals
	for (int i = -(g.getSize()); i < g.getSize(); i++)
	{
		if ((m + i) >= 0 && (m + i) < g.getSize() && (n + i) >= 0 && (n + i) < g.getSize())
		{
			if (g[m + i][n + i] == 1)
			{
				return false;
			}
		}
		if ((m + i) >= 0 && (m + i) < g.getSize() && (n - i) >= 0 && (n - i) <g.getSize())
		{
			if (g[m + i][n - i] == 1)
			{
				return false;
			}
		}
	}
	return true;
}

/**
Places divas on grid and returns true if successful
@param board the board
@param row the row to start in
*/
bool placeDivas(Grid& board, int row)
{
	int j;

	for (j = 0; j < board.getSize(); j++)
	{
		if (safeSpot(row, j, board))
		{
			if (row < board.getSize() && j < board.getSize())
			{
				board[row][j] = 1;
				if (row == (board.getSize() - 1))
				{
					return true;
				}
			}
			if (placeDivas(board, row + 1))
			{
				return true;
			}
		}
	}
	if (row == 0)
	{
		return false;
	}
	else
	{
		for (int i = 0; i < board.getSize(); i++)
		{
			if (row <board.getSize() && i<board.getSize())
				board[row - 1][i] = 0;
		}
		return false;
	}
}

