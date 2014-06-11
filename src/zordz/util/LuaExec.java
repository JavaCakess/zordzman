package zordz.util;

import java.io.File;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import zordz.entity.Entity;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;

public class LuaExec {

	static ScriptEngineManager sem;
	static ScriptEngine     engine;
	static Bindings sb;

	static String script = "";

	public static void init() {
		sem = new ScriptEngineManager();
		engine = sem.getEngineByExtension(".lua");

		sb = new SimpleBindings();
	}

	public static void script(String content) {
		script = content;
	}
	
	public static void exec_on_pickup(Level level, Player player, Entity entity) {
		try {
			CompiledScript script = ((Compilable) engine).compile(LuaExec.script);

			script.eval(sb); // Put the Lua functions into the sb environment
			LuaValue luaLvl = CoerceJavaToLua.coerce(level); // Java to Lua
			LuaValue luaPlayer = CoerceJavaToLua.coerce(player); // Java to Lua
			LuaValue luaEnt = CoerceJavaToLua.coerce(entity); // Java to Lua
			LuaFunction test = (LuaFunction) sb.get("Player_On_Pickup"); // Get Lua function
			test.call(luaLvl, luaPlayer, luaEnt); // Call the function

		} catch (Exception e) {

		}
	}

	public static void exec_on_pickup(Level level, Mob m, Entity entity) {
		try {
			CompiledScript script = ((Compilable) engine).compile(LuaExec.script);

			script.eval(sb); // Put the Lua functions into the sb environment
			LuaValue luaLvl = CoerceJavaToLua.coerce(level); // Java to Lua
			LuaValue luaMob = CoerceJavaToLua.coerce(m); // Java to Lua
			LuaValue luaEnt = CoerceJavaToLua.coerce(entity); // Java to Lua
			LuaFunction test = (LuaFunction) sb.get("Mob_On_Pickup"); // Get Lua function
			test.call(luaLvl, luaMob, luaEnt); // Call the function

		} catch (Exception e) {

		}
	}
}
