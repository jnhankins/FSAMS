#ifndef FSAMS_ENVIRONMENT_ENVIRONMENT_H
#define FSAMS_ENVIRONMENT_ENVIRONMENT_H

#include "Boundary.hpp"
#include <vector>

namespace FSAMS { namespace Environment {

class Environment {
public:
	std::vector<Boundary> walls;

	Environment() {}
	Environment(Environment& environment) : walls(environment.walls) {}
	Environment(std::vector<Boundary>& walls) : walls(walls) {}
};

} }

#endif