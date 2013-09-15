// ----------------------------------------------------------------------------
//
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
// DEALINGS IN THE SOFTWARE.
//
//
// ----------------------------------------------------------------------------
//
// Simple soccer game by Michael Holm, IO Interactive A/S
//
// I made this to learn opensteer, and it took me four hours. The players will
// hunt the ball with no team spirit, each player acts on his own accord.
//
// I challenge the reader to change the behavour of one of the teams, to beat my
// team. It should not be too hard. If you make a better team, please share the
// source code so others get the chance to create a team that'll beat yours :)
//
// You are free to use this code for whatever you please.
//
// (contributed on July 9, 2003)
//
// ----------------------------------------------------------------------------


#include <iomanip>
#include <sstream>
#include "OpenSteer/SimpleVehicle.h"
#include "OpenSteer/OpenSteerDemo.h"
#include "OpenSteer/Draw.h"

#include "Environment.hpp"
#include "Vec2.hpp"

using OpenSteer::OpenSteerDemo;
using OpenSteer::PlugIn;

class FSAMSPlugIn : public OpenSteer::PlugIn {
private:
	FSAMS::Environment::Environment environment;
	OpenSteer::SimpleVehicle camera_target;
	std::vector<OpenSteer::SimpleVehicle*> vehicles;
public:
	FSAMSPlugIn() {}

	const char* name (void) {
		return "Fire and Security Alarm Monitoring Simulation System";
	}
	
	void open(void) {
		// Create a list of all of the vehicles.
		vehicles = std::vector<OpenSteer::SimpleVehicle*>();
		vehicles.push_back(&camera_target);
		
		// Create a demo environment
		environment.walls.push_back(FSAMS::Environment::Boundary(-10, 10, 10, 10));
		environment.walls.push_back(FSAMS::Environment::Boundary( 10, 10, 10,-10));
		environment.walls.push_back(FSAMS::Environment::Boundary( 10,-10,-10,-10));
		environment.walls.push_back(FSAMS::Environment::Boundary(-10,-10,-10, 10));

		// Initialize the camera.
		camera_target.setPosition(0,0,0);
		OpenSteerDemo::init2dCamera(camera_target,0,10);
		OpenSteerDemo::camera.mode = OpenSteer::Camera::cmStraightDown;
    }

    void update(const float currentTime, const float elapsedTime) {
		// TODO
		camera_target.setPosition(camera_target.position().x,currentTime,camera_target.position().z);
    }

    void redraw(const float currentTime, const float elapsedTime) {
		// update camera, tracking test vehicle
        OpenSteerDemo::updateCamera(currentTime, elapsedTime, *OpenSteerDemo::selectedVehicle);

        // draw "ground plane"
        OpenSteerDemo::gridUtility(OpenSteer::Vec3(0,0,0));

		// draw the environment
		environment.draw();
    }

    void reset(void) {
		// TODO
    }

	void close(void) {
		// TODO
    }

    const OpenSteer::AVGroup& allVehicles(void) {
		return (const OpenSteer::AVGroup&) vehicles;
	}
};

FSAMSPlugIn pFSAMS;
