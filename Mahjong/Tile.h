/*
 * Tile.h
 *
 *  Created on: Dec 14, 2014
 *      Author: Vincent
 */

#ifndef TILE_H_
#define TILE_H_

#include <string>

class Tile{
protected:
	int type;
	int number;
public:
	Tile(int t, int n);
	~Tile();
	bool isFlower();
	bool isSpecial();
	int getType();
	int getNumber();
	std::string getStringType();
	std::string getStringNumber();
	std::string toString();
};


#endif /* TILE_H_ */
