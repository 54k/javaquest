function write(p, m) {
    p.connection.write(JSON.stringify(m));
}

function broadcast(p, m) {
    for each (var kp in p.knownListController.knownPlayers.values()) {
        kp.connection.write(JSON.stringify(m));
    }
}

exports = {
    commands: {
        "/around": function (p, m) {
            var players = 0;
            for each (var r in p.positionController.region.surroundingRegions) {
                players += r.players.size();
            }
            m.push("players around: " + Math.max(players - 1, 0));
            write(p, m);
        },
        "/who": function (p, m) {
			var table = "";
            for each (var wp in p.positionController.worldMapInstance.players.values()) {
				table += "<p>" + wp.toString() + " - " + wp.positionController.position.toString() + "</p>";
            }
			m.push(table);
			write(p, m);
        },
        "/disconnect": function (p, m) {
            p.connection.close();
        },
        "/me": function (p, m) {
            m.push(p.toString()  + " - " + p.positionController.position.toString());
            write(p, m);
        },
        "/knownlist": function (p, m) {
            var table = "";
            for each (var o in p.knownListController.knownObjects.values()) {
                table += "<p>" + o.toString() + "</p>";
            }
            m.push(table.length == 0 ? "empty" : table);
            write(p, m);
        },
        "/help": function (p, m) {
            var cnames = "";
            for (var cname in this) {
                if (cname != "/help") {
                    cnames += "<p>" + cname + "</p>";
                }
            }
            m.push(cnames);
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