// Compiled by ClojureScript 0.0-2268
goog.provide('topology.patch_math');
goog.require('cljs.core');
goog.require('util.math');
goog.require('topology.vars');
goog.require('util.math');
goog.require('topology.vars');
topology.patch_math.get_patch_at = (function get_patch_at(x,y){if((((topology.vars.min_pxcor <= x)) && ((x <= topology.vars.max_pxcor))) && (((topology.vars.min_pycor <= y)) && ((y <= topology.vars.max_pycor))))
{return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"id","id",-1388402092),(-1),new cljs.core.Keyword(null,"name","name",1843675177),("Patch "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(x)+" "+cljs.core.str.cljs$core$IFn$_invoke$arity$1(y))], null);
} else
{return null;
}
});
topology.patch_math.squash_4 = (function squash_4(v,mn){return util.math.squash.call(null,v,mn,1.0E-4);
});
topology.patch_math.wrap = (function wrap(p,mn,mx){var pos = topology.patch_math.squash_4.call(null,p,mn);if((pos >= mx))
{return (cljs.core.mod.call(null,(pos - mx),(mx - mn)) + mn);
} else
{if((pos < mn))
{return (mx - cljs.core.mod.call(null,(mn - pos),(mx - mn)));
} else
{if(new cljs.core.Keyword(null,"default","default",-1987822328))
{return pos;
} else
{return null;
}
}
}
});
topology.patch_math.wrap_y = (function wrap_y(y){if(cljs.core.truth_(topology.vars.wrap_in_y_QMARK_))
{return topology.patch_math.wrap.call(null,y,(topology.vars.min_pycor - 0.5),(topology.vars.max_pycor + 0.5));
} else
{return y;
}
});
topology.patch_math.wrap_x = (function wrap_x(x){if(cljs.core.truth_(topology.vars.wrap_in_x_QMARK_))
{return topology.patch_math.wrap.call(null,x,(topology.vars.min_pxcor - 0.5),(topology.vars.max_pxcor + 0.5));
} else
{return x;
}
});
topology.patch_math._get_patch_north = (function _get_patch_north(x,y){return topology.patch_math.get_patch_at.call(null,x,topology.patch_math.wrap_y.call(null,(y + (1))));
});
topology.patch_math._get_patch_east = (function _get_patch_east(x,y){return topology.patch_math.get_patch_at.call(null,topology.patch_math.wrap_x.call(null,(x + (1))),y);
});
topology.patch_math._get_patch_south = (function _get_patch_south(x,y){return topology.patch_math.get_patch_at.call(null,x,topology.patch_math.wrap_y.call(null,(y - (1))));
});
topology.patch_math._get_patch_west = (function _get_patch_west(x,y){return topology.patch_math.get_patch_at.call(null,topology.patch_math.wrap_x.call(null,(x - (1))),y);
});
topology.patch_math._get_patch_northeast = (function _get_patch_northeast(x,y){return topology.patch_math.get_patch_at.call(null,topology.patch_math.wrap_x.call(null,(x + (1))),topology.patch_math.wrap_y.call(null,(y + (1))));
});
topology.patch_math._get_patch_southeast = (function _get_patch_southeast(x,y){return topology.patch_math.get_patch_at.call(null,topology.patch_math.wrap_x.call(null,(x + (1))),topology.patch_math.wrap_y.call(null,(y - (1))));
});
topology.patch_math._get_patch_southwest = (function _get_patch_southwest(x,y){return topology.patch_math.get_patch_at.call(null,topology.patch_math.wrap_x.call(null,(x - (1))),topology.patch_math.wrap_y.call(null,(y - (1))));
});
topology.patch_math._get_patch_northwest = (function _get_patch_northwest(x,y){return topology.patch_math.get_patch_at.call(null,topology.patch_math.wrap_x.call(null,(x - (1))),topology.patch_math.wrap_y.call(null,(y + (1))));
});
topology.patch_math.get_patch_north = cljs.core.memoize.call(null,topology.patch_math._get_patch_north);
topology.patch_math.get_patch_east = cljs.core.memoize.call(null,topology.patch_math._get_patch_east);
topology.patch_math.get_patch_south = cljs.core.memoize.call(null,topology.patch_math._get_patch_south);
topology.patch_math.get_patch_west = cljs.core.memoize.call(null,topology.patch_math._get_patch_west);
topology.patch_math.get_patch_northeast = cljs.core.memoize.call(null,topology.patch_math._get_patch_northeast);
topology.patch_math.get_patch_southeast = cljs.core.memoize.call(null,topology.patch_math._get_patch_southeast);
topology.patch_math.get_patch_southwest = cljs.core.memoize.call(null,topology.patch_math._get_patch_southwest);
topology.patch_math.get_patch_northwest = cljs.core.memoize.call(null,topology.patch_math._get_patch_northwest);
topology.patch_math._get_neighbors_4 = (function _get_neighbors_4(x,y){return cljs.core.filter.call(null,(function (p1__19229_SHARP_){return cljs.core.not_EQ_.call(null,p1__19229_SHARP_,null);
}),new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [topology.patch_math._get_patch_north.call(null,x,y),topology.patch_math._get_patch_east.call(null,x,y),topology.patch_math._get_patch_south.call(null,x,y),topology.patch_math._get_patch_west.call(null,x,y)], null));
});
topology.patch_math._get_neighbors = (function _get_neighbors(x,y){return cljs.core.concat.call(null,topology.patch_math._get_neighbors_4.call(null,x,y),cljs.core.filter.call(null,(function (p1__19230_SHARP_){return cljs.core.not_EQ_.call(null,p1__19230_SHARP_,null);
}),new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [topology.patch_math._get_patch_northeast.call(null,x,y),topology.patch_math._get_patch_northwest.call(null,x,y),topology.patch_math._get_patch_southwest.call(null,x,y),topology.patch_math._get_patch_southeast.call(null,x,y)], null)));
});
topology.patch_math.get_neighbors_4 = (function get_neighbors_4(x,y){return cljs.core.filter.call(null,(function (p1__19231_SHARP_){return cljs.core.not_EQ_.call(null,p1__19231_SHARP_,null);
}),new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [topology.patch_math.get_patch_north.call(null,x,y),topology.patch_math.get_patch_east.call(null,x,y),topology.patch_math.get_patch_south.call(null,x,y),topology.patch_math.get_patch_west.call(null,x,y)], null));
});
topology.patch_math.get_neighbors = (function get_neighbors(x,y){return cljs.core.concat.call(null,topology.patch_math.get_neighbors_4.call(null,x,y),cljs.core.filter.call(null,(function (p1__19232_SHARP_){return cljs.core.not_EQ_.call(null,p1__19232_SHARP_,null);
}),new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [topology.patch_math.get_patch_northeast.call(null,x,y),topology.patch_math.get_patch_northwest.call(null,x,y),topology.patch_math.get_patch_southwest.call(null,x,y),topology.patch_math.get_patch_southeast.call(null,x,y)], null)));
});
topology.patch_math.shortest_x = (function shortest_x(x1,x2){return topology.patch_math.wrap_x.call(null,(x2 - x1));
});
topology.patch_math.shortest_y = (function shortest_y(y1,y2){return topology.patch_math.wrap_y.call(null,(y2 - y1));
});
