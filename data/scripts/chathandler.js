function write(p, m) {
    p.connection.write(JSON.stringify(m));
}

function broadcast(p, m) {
    for each (var kp in p.knownList.knownPlayers.values()) {
        kp.connection.write(JSON.stringify(m));
    }
}

exports = {
    commands: {
        "/position": function (p, m) {
            m.push("x: " + p.x + ", y: " + p.y);
            write(p, m);
        },
        "/around": function (p, m) {
            var players = 0;
            for each(var r in p.region.surroundingRegions) {
                players += r.players.size();
            }
            m.push("Players around: " + Math.max(players - 1, 0));
            write(p, m);
        },
        "/who": function (p, m) {
			var table = "<p>ID -- NAME -- POSITION</p>";
            for each (var wp in p.world.players.values()) {
				table += "<p>" + wp.id + " -- " + wp.name + " -- [x: " + wp.x + ", y: " + wp.y + "]</p>";
            }
			m.push(table);
			write(p, m);
        }
    },

    handle: function (player, message) {
        var m = [11, player.id];

        if (!!this.commands[message]) {
            this.commands[message](player, m);
        } else {
            m.push(message);
            write(player, m);
            broadcast(player, m);
        }
    }
};