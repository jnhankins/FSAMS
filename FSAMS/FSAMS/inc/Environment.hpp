#ifndef FSAMS_ENVIRONMENT_ENVIRONMENT_H
#define FSAMS_ENVIRONMENT_ENVIRONMENT_H


#include "Boundary.hpp"
#include "OpenSteer/Draw.h"
#include <vector>

namespace FSAMS { namespace Environment {

class Environment {
public:
	std::vector<Boundary> walls;

	Environment() {}
	Environment(const Environment& environment) : walls(environment.walls) {}
	Environment(const std::vector<Boundary>& walls) : walls(walls) {}

	void draw() const {
		for(std::size_t i=0; i<walls.size(); ++i) {
			const FSAMS::Environment::Boundary& wall = walls[i];
			OpenSteer::Vec3 color(1,1,0);
			drawLineAlpha(OpenSteer::Vec3(wall.p1.x, 0, wall.p1.z), OpenSteer::Vec3(wall.p2.x, 0, wall.p2.z), color, 1.0);
		}
	}
};

} }

#endif