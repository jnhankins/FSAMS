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

#include "Environment.hpp"
#include "Vec2.hpp"
#include "OpenSteer/SimpleVehicle.h"
#include "OpenSteer/OpenSteerDemo.h"
#include "OpenSteer/Draw.h"
#include <iomanip>
#include <sstream>
#if __APPLE__ && __MACH__
#include <GLUT/glut.h>   // for Mac OS X
#else
#include <GL/glut.h>     // for Linux and Windows
#endif

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
		OpenSteerDemo::camera.smoothMoveSpeed = 10000.0f;
    }

    void update(const float currentTime, const float elapsedTime) {
		// TODO
		//camera_target.setPosition(camera_target.position().x,currentTime,camera_target.position().z);
    }

    void redraw(const float currentTime, const float elapsedTime) {
		// Draw the camera's target (for testing).
		drawBasic2dCircularVehicle(camera_target, OpenSteer::Vec3(0,0,1));
        camera_target.drawTrail();

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

	bool handleKeyboardFuncKey (int key, bool special, bool up, bool shift, bool ctrl, bool alt) {
		const float camera_translate_step = 1;
		const float camera_rotate_step = 3.14159265359f/32.0f; // 11.25 deg
		const float camera_rotate_step_sin = std::sin(camera_rotate_step);
		const float camera_rotate_step_cos = std::cos(camera_rotate_step);

		if(special) {
			switch(key) {
			case GLUT_KEY_LEFT:
				camera_target.setPosition(camera_target.position().x+camera_translate_step,camera_target.position().y,camera_target.position().z);
				break;
			case GLUT_KEY_RIGHT: 
				camera_target.setPosition(camera_target.position().x-camera_translate_step,camera_target.position().y,camera_target.position().z);
				break;
			case GLUT_KEY_UP: 
				camera_target.setPosition(camera_target.position().x,camera_target.position().y,camera_target.position().z+camera_translate_step);
				break;
			case GLUT_KEY_DOWN: 
				camera_target.setPosition(camera_target.position().x,camera_target.position().y,camera_target.position().z-camera_translate_step);
				break;
			default:
				break;
			}
		} else {
			switch(key) {
			case '-':
			case '_':
				camera_target.setPosition(camera_target.position().x,camera_target.position().y+camera_translate_step,camera_target.position().z);
				break;
			case '=':
			case '+':
				camera_target.setPosition(camera_target.position().x,camera_target.position().y-camera_translate_step,camera_target.position().z);
				break;
			case '[': {
				float nx = camera_target.forward().x*camera_rotate_step_cos-camera_target.forward().z*camera_rotate_step_sin;
				float nz = camera_target.forward().z*camera_rotate_step_cos+camera_target.forward().x*camera_rotate_step_sin;
				camera_target.setForward( nx, 0, nz );
				} break;
			case ']': {
				float nx = camera_target.forward().x*camera_rotate_step_cos+camera_target.forward().z*camera_rotate_step_sin;
				float nz = camera_target.forward().z*camera_rotate_step_cos-camera_target.forward().x*camera_rotate_step_sin;
				camera_target.setForward( nx, 0, nz );
				} break;
			default:
				break;
			}
		}

		return true;
	}

    const OpenSteer::AVGroup& allVehicles(void) {
		return (const OpenSteer::AVGroup&) vehicles;
	}
};

FSAMSPlugIn pFSAMS;
