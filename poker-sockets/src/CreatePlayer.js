import React from "react";

export function CreatePlayer(props) {

    const handleNameChange = (event) => {
        props.setPlayerName(event.target.value);
    };

    const handleMoneyLimitChange = (event) => {
        props.setPlayerMoney(event.target.value);
    };

    const createPlayer = () => {
        const playerData = { name: props.playerName, moneyLimit: props.playerMoney};
        props.setIsPlayerCreated(true);
        props.socket.send(JSON.stringify({action: "create", data: playerData}));
    };

    return (
      <div>
          {!props.isPlayerCreated &&
              <div>
                  <h1>Welcome!</h1>
                  <h2>Please enter your name and money limit:</h2>
                  <input type="text" value={props.playerName} onChange={handleNameChange} placeholder="Enter Player Name"/>
                  <input type="text" value={props.playerMoney} onChange={handleMoneyLimitChange} placeholder="Enter Your Money Limit"/>
                  <button onClick={createPlayer}>Submit</button>
              </div>
          }
      </div>
    );
}

