local Bridge_Area_X0 = 6
local Bridge_Area_Y = 4

local HP_UID_1 = 1

local Tile = luajava.bindClass("zordz.level.tile.Tile")

function Player_On_Pickup(level, player, entity)
	if entity.uid == HP_UID_1 then
		level:setTileIDAt(Bridge_Area_X0, Bridge_Area_Y, Tile.SAND.ID)
		level:setTileIDAt(Bridge_Area_X0, Bridge_Area_Y + 1, Tile.SAND.ID)
		level:setTileIDAt(Bridge_Area_X0 + 1, Bridge_Area_Y, Tile.SAND.ID)
		level:setTileIDAt(Bridge_Area_X0 + 1, Bridge_Area_Y + 1, Tile.SAND.ID)
	end
	
end

function Mob_On_Pickup(level, mob, entity)
	
end