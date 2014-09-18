function write(p, m) {
    p.connection.write(JSON.stringify(m));
}

function broadcast(p, m) {
    for each (var kp in p.knownList.knownPlayers.values) {
        kp.connection.write(JSON.stringify(m));
    }
}

exports = {
    commands: {
        "pos": function (p, m) {
            m.push("x: " + p.x + ", y: " + p.y);
            write(p, m);
        },
        "players": function (p, m) {
            var players = 0;
            for (var r in p.region.surroundingRegions) {
                players += r.players.size;
            }
            m.push("Players around: " + players);
            write(p, m);
        },
        "online": function (p, m) {
            var table = "<table><tr><td>ID</td><td>NAME</td>POS<td></td></tr>";
            for each (var wp in p.world.players.values) {
				table += "<tr><td>" + wp.id + "</td><td>" + wp.name + "</td><td>" + wp.x + ", y: " + wp.y + "</td></tr>";
            }
			table += "</table>";
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