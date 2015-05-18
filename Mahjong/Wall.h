/*
* Wall.h
*
* Created on: May 18, 2015
* Author: Vincent
*/

#ifndef WALL_H_
#define WALL_H_

#include "Tile.h"

class Wall{
private:
	std::vector<Tile> wall;
	void fillWall();
public:
	Wall();
	~Wall();
	int getSize();
	std::string display();
	void drawFromWall(bool front);
};

#endif /* WALL_H_ */
