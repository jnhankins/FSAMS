	#ifndef FSAMS_ENVIRONMENT_ENVIRONMENT_H
#define FSAMS_ENVIRONMENT_ENVIRONMENT_H


#include "Boundary.hpp"
#include "OpenSteer/Draw.h"
#include "rapidxml.hpp"
#include <cstring>
#include <fstream>
#include <sstream>
#include <streambuf>
#include <vector>

namespace FSAMS { namespace Environment {

class Environment {
public:
	std::vector<Boundary> walls;

	Environment() {}
	Environment(const Environment& environment) : walls(environment.walls) {}
	Environment(const std::vector<Boundary>& walls) : walls(walls) {}

	void draw() const {
		const OpenSteer::Vec3 wall_color(1,1,0);
		for(std::size_t i=0; i<walls.size(); ++i) {
			const FSAMS::Environment::Boundary& wall = walls[i];
			drawLineAlpha(OpenSteer::Vec3(wall.p1.x, 0, wall.p1.z), OpenSteer::Vec3(wall.p2.x, 0, wall.p2.z), wall_color, 1.0);
		}
	}


	static void stringToFile(const std::string& xml_file_name, const std::string& text) {
		std::ofstream ofs(xml_file_name);
		ofs.write(text.c_str(), text.length());
		ofs.close();
	}
	static std::string fileToString(const std::string& xml_file_name)  {
		std::ifstream ifs;
		ifs.open(xml_file_name);
		if(ifs.is_open()) {
			std::string text;
			ifs.seekg(0, std::ios::end);   
			text.reserve((unsigned int)ifs.tellg());
			ifs.seekg(0, std::ios::beg);
			text.assign((std::istreambuf_iterator<char>(ifs)), std::istreambuf_iterator<char>());
			ifs.close();
			return text;
		} else {
			std::cerr << "Error: " << xml_file_name.c_str() << ": Could not open file." << std::endl;
			return std::string();
		}
	}


	static Environment* fromXMLFile(const std::string& xml_file_name) {
		std::string text = fileToString(xml_file_name);
		if(text.length() == 0)
			return NULL;

		char* cstr = new char[text.length() + 1];
		strcpy(cstr, text.c_str());

		rapidxml::xml_document<> doc;
		doc.parse<0>(cstr);

		Environment* env = new Environment();
		
		rapidxml::xml_node<>* floor_node = doc.first_node("floor");

		if(floor_node) {
			for(rapidxml::xml_node<>* floor_node_child = floor_node->first_node(); floor_node_child; floor_node_child = floor_node_child->next_sibling()) {
				if(floor_node_child->type()==rapidxml::node_element && strcmp(floor_node_child->name(),"walls")==0) {
					rapidxml::xml_node<>* walls_node = floor_node_child;
					for(rapidxml::xml_node<>* walls_node_child = walls_node->first_node(); walls_node_child; walls_node_child = walls_node_child->next_sibling()) {
						if(walls_node_child->type()==rapidxml::node_element && strcmp(walls_node_child->name(),"wall")==0) {
							rapidxml::xml_node<>* wall_node = walls_node_child;
							float x1, z1, x2, z2;
							std::istringstream iss(std::string(wall_node->value()));
							iss >> x1 >> z1 >> x2 >> z2;
							if(iss.fail()) {
								std::cerr << "Error: " << xml_file_name.c_str() << ": Could not read wall coordinates: " << wall_node->value() << std::endl;
								return NULL;
							}
							env->walls.push_back(Boundary(x1, z1, x2, z2));
						}
					} 
				}
			}
		}

		return env;
	}
	void toXMLFile(const std::string& xml_file_name) const {
		
		//xml_node<> *node = doc.allocate_node(node_element, "a", "Google");
	}

};

} }

#endif