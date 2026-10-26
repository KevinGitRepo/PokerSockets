import React from "react";
import "./CreatePlayer.css";

export function CreatePlayer(props) {

    const handleNameChange = (event) => {
        props.setPlayerName(event.target.value);
    };

    const handleMoneyLimitChange = (event) => {
        props.setPlayerMoney(event.target.value);
    };

    const createPlayer = () => {
        const playerData = { name: props.playerName, money: props.playerMoney};
        props.setIsPlayerCreated(true);
        props.socket.send(JSON.stringify({action: "create", data: playerData}));
    };

    return (
      <div>
          {!props.isPlayerCreated &&
              <div className={"create-player"}>
                  <h1 className={"create-player-title"}>Welcome!</h1>
                  <h2 className={"create-player-subtitle"}>Please enter your name and money limit:</h2>
                  <input className={"create-player-input"} type="text" value={props.playerName} onChange={handleNameChange} placeholder="Enter Player Name"/>
                  <input className={"create-player-input"} type="text" onChange={handleMoneyLimitChange} placeholder="Enter Your Money Limit"/>
                  <button className={"create-player-button"} onClick={createPlayer}>Submit</button>
              </div>
          }
      </div>
    );
}

