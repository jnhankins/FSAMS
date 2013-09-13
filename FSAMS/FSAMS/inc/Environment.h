#ifndef ENVIRONMENT_H
#define ENVIRONMENT_H

#include <vector>


class Wall {
public:
	float x1, y1, x2, y2;

	Wall(float x1, float y1, float x2, float y2) :
		x1(x1), y1(y1), x2(x2), y2(y2) {}
};

typedef std::vector<Wall> Room;

class AlarmSystemComponent {
public:
	virtual int getID();
	virtual int getType();
	virtual std::pair<int,int> getCoordinates();
};

class Environment {
public:
	std::vector<Wall> walls;
	std::vector<Room> rooms;
	std::vector<AlarmSystemComponent> ascomps;
};

#endif