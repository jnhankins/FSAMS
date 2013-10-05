#ifndef FSAMS_ENVIRONMENT_BOUNDARY_H
#define FSAMS_ENVIRONMENT_BOUNDARY_H

#include "Vec2.hpp"

namespace FSAMS { namespace Environment {

typedef struct Boundary {
	Vec2 p1, p2;

	Boundary(float x1, float y1, float x2, float y2) : p1(x1,y1), p2(x2,y2) {}
	Boundary(const Vec2& p1, const Vec2& p2) : p1(p1), p2(p2) {}
	Boundary(const Boundary& boundary) : p1(boundary.p1), p2(boundary.p2) {}
	
	inline void set(const Vec2& p1, const Vec2& p2) {
		this->p1.set(p1);
		this->p2.set(p2);
	}
	inline void set(float x1, float z1, float x2, float z2) {
		this->p1.set(x1,z1);
		this->p2.set(x2,z2);
	}
} Boundary;

} }  // namespace

#endif