#ifndef FSAMS_ENVIRONMENT_VEC2_H
#define FSAMS_ENVIRONMENT_VEC2_H

namespace FSAMS { namespace Environment {

typedef struct Vec2 {
	float x, z;

	Vec2(float x, float z) : x(x), z(z) {}
	
	void set(float x, float z) {
		this->x = x;
		this->z = z;
	}
} Vec2;

} }  // namespace

#endif