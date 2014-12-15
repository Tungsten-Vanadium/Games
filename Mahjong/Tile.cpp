/*
 * Tile.cpp
 *
 *  Created on: Dec 14, 2014
 *      Author: Vincent
 */

#include "Tile.h"
#include <sstream>

Tile::Tile(int t, int n) {
	type = t;
	number = n;
}

Tile::~Tile() {

}

// Flowers are special tiles that are shown upon receiving them. They count for points in the end
bool Tile::isFlower() {
	return type == 1 && number < 3;
}

// Special tiles cannot be put in a sequence
bool Tile::isSpecial() {
	return type == 1;
}

// Used for calculations
int Tile::getType() {
	return type;
}

int Tile::getNumber() {
	return number;
}

// Used for display to console
std::string Tile::getStringType() {
	switch (type) {
	case 1:
		return "Special";
	case 2:
		return "Character";
	case 3:
		return "Dot";
	case 4:
		return "Bamboo";
	default:
		return "Error";
	}
}

std::string Tile::getStringNumber() {
	std::string ret = "Empty";
	if(type == 0)
		return ret;
	if(type == 1){  // Special tiles are uniquely named while the rest are just type and number
		switch (number){
		case 1:
		case 2:
			ret = "Flower";
			break;
		case 3:
			ret = "East";
			break;
		case 4:
			ret = "South";
			break;
		case 5:
			ret = "West";
			break;
		case 6:
			ret = "North";
			break;
		case 7:
			ret = "Red dragon";
			break;
		case 8:
			ret = "Green dragon";
			break;
		case 9:
			ret = "White dragon";
		}
	} else {
		std::stringstream ss;
		ss << number;
		ret = ss.str();
	}
	return ret;
}

std::string Tile::toString() {
	if(type == 1)
		return getStringNumber();
	return getStringType() + " " + getStringNumber();
}
