// Compiled by ClojureScript 0.0-2268
goog.provide('util.math');
goog.require('cljs.core');
util.math.clamp = (function clamp(v,mn,mx){if((v > mx))
{return mx;
} else
{if((v < mn))
{return mn;
} else
{if(new cljs.core.Keyword(null,"default","default",-1987822328))
{return v;
} else
{return null;
}
}
}
});
util.math.squash = (function squash(v,to,precision){if((Math.abs.call(null,(v - to)) < precision))
{return to;
} else
{return v;
}
});
