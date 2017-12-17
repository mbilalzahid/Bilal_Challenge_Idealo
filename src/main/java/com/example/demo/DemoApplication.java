package com.example.demo;


import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ToyRobotException;
import com.example.demo.simulator.SquareBoard;
import com.example.demo.simulator.ToyRobot;
import com.example.demo.Game;


@RestController
@EnableAutoConfiguration
public class DemoApplication {

	@RequestMapping(value="/PLAY", headers="Content-Type=application/json", method = RequestMethod.POST)
	 @ResponseBody
	 String output(@RequestBody Map<String, Object> gameplay) {
	    
	    @SuppressWarnings("unchecked")
		List<String> gameplayList = (List<String>)gameplay.get("GAMEPLAY");
	    
	    String[] placeRobot  = gameplayList.get(0).split(" ");
        if(placeRobot.length < 2){
        	return "ROBOT MISSING";
        }
        
	    SquareBoard squareBoard = new SquareBoard(4, 4);
        ToyRobot robot = new ToyRobot();
        Game game = new Game(squareBoard, robot);
		
        String outputVal = null;
        for (String item : gameplayList) {
        	try {
                outputVal = game.eval(item);
            } catch (ToyRobotException e) {
                return e.getMessage();
            }
        }
	 	    
	    return "OUTPUT: " + outputVal;
	 }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
