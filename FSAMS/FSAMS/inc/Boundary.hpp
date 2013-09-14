#ifndef FSAMS_ENVIRONMENT_BOUNDARY_H
#define FSAMS_ENVIRONMENT_BOUNDARY_H

#include "Vec2.hpp"

namespace FSAMS { namespace Environment {

typedef struct Boundary {
	Vec2 p1, p2;

	Boundary(Vec2 p1, Vec2 p2) : p1(p1), p2(p2) {}
	Boundary(float x1, float y1, float x2, float y2) : p1(Vec2(x1,y1)), p2(Vec2(x2,y2)) {}
	
	void set(Vec2 p1, Vec2 p2) {
		this->p1 = p1;
		this->p2 = p2;
	}
	void set(float x1, float z1, float x2, float z2) {
		this->p1.x = x1;
		this->p1.z = z1;
		this->p2.x = x2;
		this->p2.z = z2;
	}
} Boundary;

} }  // namespace

#endif