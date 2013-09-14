#ifndef FSAMS_ENVIRONMENT_VEC2_H
#define FSAMS_ENVIRONMENT_VEC2_H

namespace FSAMS { namespace Environment {

typedef struct Vec2 {
	float x, z;

	Vec2() : x(0), z(0) {}
	Vec2(float x, float z) : x(x), z(z) {}
	Vec2(const Vec2& p) : x(p.x), y(p.z) {}
	
	inline void set(float x, float z) {
		this->x = x;
		this->z = z;
	}
	inline void set(const Vec2& p) {
		set(p.x,p.z);
	}
} Vec2;

} }  // namespace

#endif