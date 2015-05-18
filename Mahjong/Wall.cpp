/*
* Wall.cpp
*
* Created on: Dec 14, 2014
* Author: Vincent
*/

#include "Wall.h"
#include <stdlib.h>
#include <time.h> 

Wall::Wall() {
	fillWall();
}

Wall::~Wall() {
	wall.clear();
}

void Wall::fillWall() {
  std::list<Tile> tempWall;
  for(int suit = 1; suit <= 4; suit++) {
    for(int num = 1; num <= 9; num++) {
      tempWall.add(new Tile(suit, num));
    }
  }
  srand (time(NULL));
  for(int tile = 0; tile < 144; tile++) {
    wall.add(tempWall.erase(rand % tempWall.size()));
  }
}

int Wall::getSize() {
	return wall.size();
}

std::string Wall::display() {

}

Tile Wall::drawFromWall(bool front) {
  if(front == true) {
    return wall.pop_front();
  }else{
    return wall.pop_back();
  }
}
