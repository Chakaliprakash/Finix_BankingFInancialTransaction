import React, { useMemo, useState } from "react";
import {
  Activity, ArrowDownLeft, ArrowDownRight, ArrowLeftRight, ArrowUpRight,
  Bell, BriefcaseBusiness, Building2, CalendarDays, Check, ChevronDown,
  ChevronLeft, ChevronRight, CircleHelp, CreditCard, Download, Eye, EyeOff,
  FileText, Filter, Home, Landmark, LogOut, Menu, MoreHorizontal, Plus,
  Search, Settings, ShieldCheck, Sparkles, TrendingUp, Wallet, X
} from "lucide-react";

const initialTransactions = [
  { id: "TXN-48291", name: "Spotify Premium", category: "Subscription", date: "Today, 10:42 AM", amount: -119, status: "Completed", initials: "S", tone: "green" },
  { id: "TXN-48290", name: "Salary — Infitech Global", category: "Income", date: "Today, 09:15 AM", amount: 68500, status: "Completed", initials: "IG", tone: "blue" },
  { id: "TXN-48289", name: "Amazon India", category: "Shopping", date: "Yesterday, 06:28 PM", amount: -2499, status: "Completed", initials: "a", tone: "orange" },
  { id: "TXN-48288", name: "Rahul Sharma", category: "Transfer", date: "Yesterday, 02:10 PM", amount: -1500, status: "Completed", initials: "RS", tone: "purple" },
  { id: "TXN-48287", name: "Swiggy", category: "Food & dining", date: "Oct 30, 08:12 PM", amount: -348, status: "Pending", initials: "S", tone: "coral" }
];

const accounts = [
  { name: "Everyday Savings", number: "••••  4829", type: "Savings account", balance: 184250.75, icon: Wallet, color: "mint", tag: "Primary" },
  { name: "Current Account", number: "••••  9012", type: "Current account", balance: 72500, icon: BriefcaseBusiness, color: "lavender", tag: "Business" },
  { name: "Fixed Deposit", number: "••••  1138", type: "Fixed deposit", balance: 250000, icon: Landmark, color: "peach", tag: "7.1% p.a." }
];

const money = (value, compact = false) => new Intl.NumberFormat("en-IN", {
  style: "currency", currency: "INR", maximumFractionDigits: compact ? 0 : 2
}).format(value);

function Brand() {
  return <div className="brand"><div className="brand-mark">f<span>.</span></div><span>finix</span></div>;
}

function Sidebar({ active, onNavigate, mobileOpen, onClose }) {
  const groups = [
    { label: "WORKSPACE", items: [{ id: "Overview", icon: Home }, { id: "Accounts", icon: Wallet }, { id: "Transfers", icon: ArrowLeftRight }, { id: "Transactions", icon: Activity }, { id: "Cards", icon: CreditCard }] },
    { label: "PREFERENCES", items: [{ id: "Settings", icon: Settings }, { id: "Help & support", icon: CircleHelp }] }
  ];
  return <>
    {mobileOpen && <button className="mobile-scrim" onClick={onClose} aria-label="Close menu" />}
    <aside className={`sidebar ${mobileOpen ? "sidebar-open" : ""}`}>
      <div className="sidebar-top"><Brand /><button className="icon-button close-menu" onClick={onClose}><X size={18}/></button></div>
      <div className="workspace-switch"><div className="workspace-avatar">P</div><div><strong>Personal workspace</strong><small>Free plan</small></div><ChevronDown size={15}/></div>
      {groups.map(group => <div className="nav-group" key={group.label}>
        <p className="nav-label">{group.label}</p>
        {group.items.map(item => <button key={item.id} onClick={() => { onNavigate(item.id); onClose(); }} className={`nav-item ${active === item.id ? "active" : ""}`}>
          <item.icon size={18} strokeWidth={1.8}/><span>{item.id}</span>{item.id === "Transactions" && <span className="nav-count">5</span>}
        </button>)}
      </div>)}
      <div className="sidebar-bottom">
        <div className="upgrade-card"><div className="upgrade-icon"><Sparkles size={16}/></div><strong>Make more of Finix</strong><p>Get smarter insights and unlimited transfers.</p><button onClick={() => onNavigate("Settings")}>Explore plans <ArrowUpRight size={14}/></button></div>
        <div className="profile-row"><div className="profile-avatar">PC</div><div className="profile-copy"><strong>Prakash Chakali</strong><small>prakash@gmail.com</small></div><MoreHorizontal size={19}/></div>
      </div>
    </aside>
  </>;
}

function BalanceChart() {
  const bars = [34, 46, 40, 62, 52, 74, 58, 68, 48, 83, 66, 92];
  return <div className="chart-wrap">
    <div className="chart-y-labels"><span>₹2.5L</span><span>₹1.8L</span><span>₹1.2L</span><span>₹0.5L</span></div>
    <div className="chart-main">
      <div className="chart-grid"><i/><i/><i/><i/></div>
      <div className="chart-bars">{bars.map((height, i) => <div className="bar-column" key={i}><span className={i === 9 ? "bar-highlight" : ""} style={{height: height + "%"}} /></div>)}</div>
      <div className="chart-x-labels"><span>May</span><span>Jun</span><span>Jul</span><span>Aug</span><span>Sep</span><span>Oct</span></div>
    </div>
  </div>;
}

function Modal({ open, onClose, onTransfer }) {
  const [from, setFrom] = useState("Everyday Savings");
  const [recipient, setRecipient] = useState("");
  const [account, setAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [note, setNote] = useState("");
  const [error, setError] = useState("");
  if (!open) return null;
  const submit = (event) => {
    event.preventDefault();
    const parsed = Number(amount);
    if (!recipient.trim() || !account.trim() || !Number.isFinite(parsed) || parsed <= 0) {
      setError("Enter recipient details and a valid amount.");
      return;
    }
    onTransfer({ from, recipient: recipient.trim(), account, amount: parsed, note });
    setRecipient(""); setAccount(""); setAmount(""); setNote(""); setError(""); onClose();
  };
  return <div className="modal-backdrop" onMouseDown={onClose}>
    <div className="transfer-modal" onMouseDown={e => e.stopPropagation()}>
      <div className="modal-heading"><div><span className="eyebrow">PAYMENT</span><h2>Send money</h2><p>Transfer securely to another account.</p></div><button className="icon-button" onClick={onClose}><X size={19}/></button></div>
      <form onSubmit={submit}>
        <label>From account<select value={from} onChange={e => setFrom(e.target.value)}><option>Everyday Savings</option><option>Current Account</option></select></label>
        <label>Recipient name<input value={recipient} onChange={e => setRecipient(e.target.value)} placeholder="e.g. Rahul Sharma"/></label>
        <label>Account number / UPI ID<input value={account} onChange={e => setAccount(e.target.value)} placeholder="Enter account or UPI ID"/></label>
        <label>Amount (INR)<div className="amount-input"><span>₹</span><input type="number" min="1" step="0.01" value={amount} onChange={e => setAmount(e.target.value)} placeholder="0.00"/></div></label>
        <label>Note <span className="optional">(optional)</span><input value={note} onChange={e => setNote(e.target.value)} placeholder="What's this for?"/></label>
        {error && <p className="form-error">{error}</p>}
        <div className="secure-note"><ShieldCheck size={16}/> Your transfer is protected with Finix secure demo mode.</div>
        <button className="primary-button full-button" type="submit">Continue <ArrowUpRight size={17}/></button>
      </form>
    </div>
  </div>;
}

export default function App() {
  const [active, setActive] = useState("Overview");
  const [mobileOpen, setMobileOpen] = useState(false);
  const [showBalance, setShowBalance] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [query, setQuery] = useState("");
  const [filter, setFilter] = useState("All");
  const [transactions, setTransactions] = useState(initialTransactions);
  const [notice, setNotice] = useState("");

  const handleHeroPointer = (event) => {
    const bounds = event.currentTarget.getBoundingClientRect();
    const x = (event.clientX - bounds.left) / bounds.width;
    const y = (event.clientY - bounds.top) / bounds.height;
    event.currentTarget.style.setProperty("--pointer-x", `${x * 100}%`);
    event.currentTarget.style.setProperty("--pointer-y", `${y * 100}%`);
    const card = event.currentTarget.querySelector(".finix-card-3d");
    if (card && window.matchMedia("(prefers-reduced-motion: no-preference)").matches) {
      card.style.setProperty("--tilt-x", `${(0.5 - y) * 15}deg`);
      card.style.setProperty("--tilt-y", `${(x - 0.5) * 22}deg`);
    }
  };

  const resetHeroPointer = (event) => {
    const card = event.currentTarget.querySelector(".finix-card-3d");
    if (card) {
      card.style.removeProperty("--tilt-x");
      card.style.removeProperty("--tilt-y");
    }
  };

  const filteredTransactions = useMemo(() => transactions.filter(tx => {
    const matchesQuery = (tx.name + tx.category + tx.id).toLowerCase().includes(query.toLowerCase());
    const matchesFilter = filter === "All" || (filter === "Income" ? tx.amount > 0 : filter === "Expense" ? tx.amount < 0 : tx.category === "Transfer");
    return matchesQuery && matchesFilter;
  }), [transactions, query, filter]);

  const totalBalance = 184250.75 + 72500 + 250000;
  const handleTransfer = ({ recipient, account, amount, note }) => {
    setTransactions(current => [{
      id: "TXN-" + Math.floor(49000 + Math.random() * 900), name: recipient, category: "Transfer",
      date: "Just now", amount: -amount, status: "Completed", initials: recipient.slice(0, 1).toUpperCase(), tone: "purple", note, account
    }, ...current]);
    setNotice(`Demo transfer of ${money(amount)} to ${recipient} added to activity.`);
    window.setTimeout(() => setNotice(""), 4500);
  };

  const navigate = (page) => {
    setActive(page);
    if (page !== "Overview") {
      setNotice(`${page} view selected. This dashboard section is ready for API integration.`);
      window.setTimeout(() => setNotice(""), 3500);
    }
  };

  return <div className="app-shell">
    <Sidebar active={active} onNavigate={navigate} mobileOpen={mobileOpen} onClose={() => setMobileOpen(false)}/>
    <main className="main-panel">
      <header className="topbar">
        <div className="topbar-left"><button className="icon-button mobile-menu" onClick={() => setMobileOpen(true)}><Menu size={20}/></button><div className="breadcrumb">Workspace <ChevronRight size={14}/> <strong>{active}</strong></div></div>
        <div className="topbar-actions">
          <div className="search-box"><Search size={16}/><input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search transactions..."/><kbd>⌘ K</kbd></div>
          <button className="icon-button notification-button" aria-label="Notifications"><Bell size={19}/><i/></button>
          <div className="top-avatar">PC</div>
        </div>
      </header>

      <div className="page-content">
        {notice && <div className="notice"><Check size={16}/>{notice}<button onClick={() => setNotice("")}><X size={15}/></button></div>}
        <section className="welcome-row">
          <div><div className="eyebrow">MONDAY, NOVEMBER 02, 2026</div><h1>Good morning, Prakash <span className="wave">✦</span></h1><p>Here's what's happening with your money today.</p></div>
          <div className="welcome-actions"><button className="secondary-button" onClick={() => setNotice("Your monthly report is being prepared in demo mode.")}><Download size={16}/> Export report</button><button className="primary-button" onClick={() => setModalOpen(true)}><Plus size={17}/> New transfer</button></div>
        </section>

        <section className="finix-hero-3d" aria-label="Finix premium banking card" onPointerMove={handleHeroPointer} onPointerLeave={resetHeroPointer}>
          <div className="hero-copy-3d">
            <span className="hero-kicker"><span className="hero-kicker-dot"/> THE NEXT GENERATION OF BANKING</span>
            <h2>Money moves.<br/><em>You move forward.</em></h2>
            <p>A clearer view of your money, with every account and transaction in one beautifully connected space.</p>
            <div className="hero-trust-row"><span><ShieldCheck size={15}/> Secure workspace</span><span><Activity size={15}/> Live overview</span></div>
          </div>
          <div className="hero-visual-3d" aria-hidden="true">
            <div className="hero-orbit orbit-one"/><div className="hero-orbit orbit-two"/>
            <div className="hero-glow"/>
            <div className="floating-chip"><Sparkles size={13}/> FINIX PREMIUM</div>
            <div className="finix-card-3d">
              <div className="card-topline"><span className="card-brand">finix<span>.</span></span><span className="card-contactless">)))</span></div>
              <div className="card-chip-3d"><i/><i/><i/><i/></div>
              <div className="card-number-3d">•••• &nbsp; •••• &nbsp; •••• &nbsp; 4829</div>
              <div className="card-bottomline"><div><small>ACCOUNT HOLDER</small><strong>PRAKASH CHAKALI</strong></div><div className="card-network"><i/><i/></div></div>
              <div className="card-glass-shine"/>
            </div>
            <div className="floating-stat"><span className="stat-icon-3d"><TrendingUp size={15}/></span><div><small>MONTHLY GROWTH</small><strong>+8.24%</strong></div><span className="stat-spark">↗</span></div>
            <div className="floating-ring ring-a"/><div className="floating-ring ring-b"/>
          </div>
          <div className="hero-bottom-glow"/>
        </section>

        <section className="summary-grid">
          <div className="balance-card">
            <div className="balance-card-top"><div className="balance-label"><span className="balance-dot"/> TOTAL ACCOUNT BALANCE</div><button className="balance-eye" onClick={() => setShowBalance(v => !v)} aria-label="Toggle balance visibility">{showBalance ? <Eye size={17}/> : <EyeOff size={17}/>}</button></div>
            <div className="balance-amount">{showBalance ? money(totalBalance) : "₹ ••••••••"}</div>
            <div className="balance-change"><span><TrendingUp size={14}/> 8.24%</span> <span>vs. last month</span></div>
            <div className="balance-card-footer"><div><small>Available balance</small><strong>{showBalance ? money(256750.75) : "₹ ••••••"}</strong></div><div className="balance-card-chip"><div/><div/></div></div>
            <div className="balance-decoration one"/><div className="balance-decoration two"/>
          </div>
          <div className="metric-card"><div className="metric-top"><span className="metric-icon income"><ArrowDownLeft size={18}/></span><span className="metric-period">This month <ChevronDown size={13}/></span></div><p>Total income</p><h2>{money(68500)}</h2><div className="metric-foot positive"><TrendingUp size={14}/> 12.8% <span>vs last month</span></div><div className="mini-bars">{[25,42,32,58,45,75,55,88,64,100,73,91].map((v,i)=><i key={i} style={{height:v+"%"}}/>)}</div></div>
          <div className="metric-card"><div className="metric-top"><span className="metric-icon expense"><ArrowUpRight size={18}/></span><span className="metric-period">This month <ChevronDown size={13}/></span></div><p>Total expenses</p><h2>{money(18460)}</h2><div className="metric-foot negative"><ArrowDownRight size={14}/> 4.3% <span>vs last month</span></div><div className="mini-bars expense-bars">{[72,48,62,36,52,30,65,42,57,34,46,28].map((v,i)=><i key={i} style={{height:v+"%"}}/>)}</div></div>
        </section>

        <section className="content-grid">
          <div className="panel balance-history">
            <div className="panel-heading"><div><h3>Balance history</h3><p>Your account balance over time</p></div><button className="select-button">Last 6 months <ChevronDown size={14}/></button></div>
            <div className="chart-legend"><span><i className="legend-dot primary-dot"/> Total balance</span><span className="chart-total">{showBalance ? money(totalBalance) : "₹ •••••"}</span></div>
            <BalanceChart/>
          </div>
          <div className="panel spending-panel">
            <div className="panel-heading"><div><h3>Spending overview</h3><p>Where your money goes</p></div><button className="more-button"><MoreHorizontal size={19}/></button></div>
            <div className="spending-total"><strong>{money(18460)}</strong><span>This month</span></div>
            <div className="donut-area"><div className="donut"><div className="donut-center"><strong>5</strong><span>Categories</span></div></div></div>
            <div className="spending-legend">
              <div><i className="spend-dot spend-purple"/><span>Shopping</span><strong>38%</strong></div>
              <div><i className="spend-dot spend-blue"/><span>Food & dining</span><strong>26%</strong></div>
              <div><i className="spend-dot spend-mint"/><span>Bills & utilities</span><strong>20%</strong></div>
              <div><i className="spend-dot spend-orange"/><span>Others</span><strong>16%</strong></div>
            </div>
          </div>
        </section>

        <section className="panel accounts-panel">
          <div className="panel-heading"><div><h3>Your accounts</h3><p>Manage and track your bank accounts</p></div><button className="text-button" onClick={() => navigate("Accounts")}>View all accounts <ArrowUpRight size={15}/></button></div>
          <div className="accounts-grid">{accounts.map((account) => <div className="account-card" key={account.number}><div className="account-card-top"><span className={`account-icon ${account.color}`}><account.icon size={19}/></span><span className="account-tag">{account.tag}</span><button className="more-button"><MoreHorizontal size={18}/></button></div><p>{account.name}</p><small>{account.type} · {account.number}</small><strong>{showBalance ? money(account.balance) : "₹ ••••••"}</strong><div className="account-progress"><i style={{width: account.name === "Fixed Deposit" ? "78%" : account.name === "Everyday Savings" ? "62%" : "38%"}}/></div></div>)}</div>
        </section>

        <section className="panel transactions-panel">
          <div className="panel-heading transactions-heading"><div><h3>Recent transactions</h3><p>Keep track of your latest activity</p></div><div className="transaction-actions"><button className="select-button filter-button" onClick={() => setFilter(current => current === "All" ? "Income" : current === "Income" ? "Expense" : current === "Expense" ? "Transfer" : "All")}><Filter size={14}/>{filter}<ChevronDown size={13}/></button><button className="text-button" onClick={() => navigate("Transactions")}>View all <ArrowUpRight size={15}/></button></div></div>
          <div className="table-wrap"><table><thead><tr><th>NAME / DESCRIPTION</th><th>DATE</th><th>AMOUNT</th><th>STATUS</th><th/></tr></thead><tbody>
            {filteredTransactions.map(tx => <tr key={tx.id}><td><div className="transaction-name"><span className={`transaction-avatar ${tx.tone}`}>{tx.initials}</span><div><strong>{tx.name}</strong><small>{tx.category} · {tx.id}</small></div></div></td><td className="date-cell">{tx.date}</td><td className={`amount-cell ${tx.amount > 0 ? "amount-positive" : ""}`}>{tx.amount > 0 ? "+" : "−"}{money(Math.abs(tx.amount))}</td><td><span className={`status-pill ${tx.status.toLowerCase()}`}><i/>{tx.status}</span></td><td><button className="more-button"><MoreHorizontal size={18}/></button></td></tr>)}
          </tbody></table>{filteredTransactions.length === 0 && <div className="empty-state">No transactions match your search.</div>}</div>
          <div className="table-footer"><span>Showing {filteredTransactions.length} of {transactions.length} transactions</span><div><button className="pagination-button" disabled><ChevronLeft size={16}/></button><button className="pagination-button selected-page">1</button><button className="pagination-button" disabled><ChevronRight size={16}/></button></div></div>
        </section>

        <footer className="footer"><span>© 2026 Finix Financial Technologies</span><div><span><i className="online-dot"/> All systems operational</span><a href="#privacy">Privacy</a><a href="#terms">Terms</a></div></footer>
      </div>
    </main>
    <Modal open={modalOpen} onClose={() => setModalOpen(false)} onTransfer={handleTransfer}/>
  </div>;
}